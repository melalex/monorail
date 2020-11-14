terraform {
  backend "gcs" {
    bucket = "melalex-terraform-admin"
    prefix = "state/monorail"
  }
}

provider "google" {
  region = var.region
  zone   = var.zone
}

locals {
  network_name = "default"
  app_name     = "monorail"
  app_origin   = "terraform"
}

data "template_file" "ansible_inventory" {
  template = file(var.ansible_inventory_file_template)

  vars = {
    app_hosts = google_compute_instance.this.network_interface.0.access_config.0.nat_ip

    ssh_user             = var.compute_instance_username
    ssh_private_key_file = abspath(local_file.private_key.filename)
    monorail_version     = var.app_version
    github_token         = var.github_token
  }
}

resource "google_compute_instance" "this" {
  name         = "${local.app_name}-app"
  machine_type = "f1-micro"
  project      = var.project

  metadata = {
    ssh-keys = "${var.compute_instance_username}:${tls_private_key.this.public_key_openssh}"
  }

  labels = {
    app    = local.app_name
    owner  = var.app_owner
    origin = local.app_origin
  }

  boot_disk {
    initialize_params {
      image = "ubuntu-os-cloud/ubuntu-minimal-2004-lts"
    }
  }

  network_interface {
    network = local.network_name

    access_config {
      // Include this section to give the VM an external ip address
    }
  }

  depends_on = [
    google_project_service.firestore
  ]

  provisioner "remote-exec" {
    inline = [
      "echo 'Up and running'"
    ]

    connection {
      host        = self.network_interface.0.access_config.0.nat_ip
      type        = "ssh"
      user        = var.compute_instance_username
      private_key = file(local_file.private_key.filename)
    }
  }

  provisioner "local-exec" {
    command = "ssh-keyscan -H ${self.network_interface.0.access_config.0.nat_ip} >> ~/.ssh/known_hosts"
  }
}

resource "null_resource" "this" {
  depends_on = [
    google_compute_instance.this
  ]

  triggers = {
    always_run = timestamp()
  }

  provisioner "local-exec" {
    command = <<EOT
      ansible-galaxy install -r ${var.ansible_playbook_location}/requirements.yml
      ansible-galaxy collection install -r ${var.ansible_playbook_location}/requirements.yml
      ansible-playbook -i ${local_file.ansible_inventory.filename} ${var.ansible_playbook_location}/playbook.yml
    EOT
  }
}

resource "google_project_service" "firestore" {
  project = var.project
  service = "firestore.googleapis.com"

  disable_dependent_services = true
}

resource "google_compute_firewall" "this" {
  name    = "${google_compute_instance.this.name}-firewall"
  network = local.network_name
  project = var.project

  allow {
    protocol = "tcp"
    ports = [
      "8080"
    ]
  }
}

resource "tls_private_key" "this" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "local_file" "private_key" {
  sensitive_content = tls_private_key.this.private_key_pem
  file_permission   = "0600"
  filename          = "${var.ssh_keys_folder}/id_rsa"
}

resource "local_file" "public_key" {
  sensitive_content = tls_private_key.this.public_key_pem
  file_permission   = "0644"
  filename          = "${var.ssh_keys_folder}/id_rsa.pub"
}

resource "local_file" "ansible_inventory" {
  content  = data.template_file.ansible_inventory.rendered
  filename = var.ansible_inventory_file
}
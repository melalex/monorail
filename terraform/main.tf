terraform {
  backend "gcs" {
    bucket = "melalex-terraform-admin"
    prefix = "state/monorail"
  }
}

provider "google" {
  region = var.region
  zone = var.zone
  credentials = file(var.credentials_file_path)
}

locals {
  network_name = "default"
  app_name = "monorail"
  app_origin = "terraform"
}

resource "google_compute_instance" "this" {
  name = "${local.app_name}-app"
  machine_type = "f1-micro"
  project = var.project

  metadata = {
    ssh-keys = "${var.compute_instance_username}:${tls_private_key.this.public_key_openssh}"
  }

  labels = {
    app = local.app_name
    owner = var.app_owner
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
      host = google_compute_instance.this.network_interface.0.access_config.0.nat_ip
      type = "ssh"
      user = var.compute_instance_username
      private_key = local_file.private_key.filename
    }
  }

  provisioner "local-exec" {
    command = "ssh-keyscan -H ${google_compute_instance.this.network_interface.0.access_config.0.nat_ip} >> ~/.ssh/known_hosts"
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
      ansible-playbook \
        -i '${google_compute_instance.this.network_interface.0.access_config.0.nat_ip},' \
        --private-key ${local_file.private_key.filename} \
        ${var.ansible_playbook_location}/playbook.yml \
        -u ${var.compute_instance_username} \
        --extra-vars 'monorail_version=${var.app_version}'
    EOT
  }
}

resource "google_project_service" "firestore" {
  project = var.project
  service = "firestore.googleapis.com"

  disable_dependent_services = true
}

resource "google_compute_firewall" "this" {
  name = "${google_compute_instance.this.name}-firewall"
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
  rsa_bits = 4096
}

resource "local_file" "private_key" {
  content = tls_private_key.this.private_key_pem
  filename = "${var.ssh_keys_folder}/id_rsa"
}

resource "local_file" "public_key" {
  content = tls_private_key.this.public_key_pem
  filename = "${var.ssh_keys_folder}/id_rsa.pub"
}

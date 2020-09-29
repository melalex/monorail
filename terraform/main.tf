terraform {
  backend "gcs" {
    bucket = "monorail-ops"
    prefix = "terraform/state"
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
}

resource "google_project_service" "firestore" {
  project = var.project
  service = "firestore.googleapis.com"

  disable_dependent_services = true
}

resource "tls_private_key" "this" {
  algorithm = "RSA"
  rsa_bits = 4096
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
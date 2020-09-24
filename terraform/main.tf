terraform {
  backend "gcs" {
    bucket = "monorail-ops"
    prefix = "terraform/state"
  }
}

provider "google" {
  project = "main-290415"
  region = "us-east1"
  zone = "us-east1-b"
}

resource "google_compute_instance" "this" {
  name = "monorail-app"
  machine_type = "f1-micro"

  boot_disk {
    initialize_params {
      image = "ubuntu-os-cloud/ubuntu-minimal-2004-lts"
    }
  }

  network_interface {
    network = "default"

    access_config {
      // Ephemeral IP
    }
  }
}
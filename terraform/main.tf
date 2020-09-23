terraform {
  backend "gcs" {
    bucket = "monorail-ops"
    prefix = "terraform/state"
  }
}

provider "google" {
  project = "main-290415"
  region = "us-east1"
}

data "google_compute_zones" "this" {}

resource "google_compute_instance" "this" {
  name = "monorail-app"
  machine_type = "n1-standard-1"
  zone = google_compute_zones.this.names[0]

  tags = [
    "foo",
    "bar"
  ]

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }

  // Local SSD disk
  scratch_disk {
    interface = "SCSI"
  }

  network_interface {
    network = "default"

    access_config {
      // Ephemeral IP
    }
  }

  metadata = {
    foo = "bar"
  }

  metadata_startup_script = "echo hi > /test.txt"

  service_account {
    scopes = [
      "userinfo-email",
      "compute-ro",
      "storage-ro"]
  }
}
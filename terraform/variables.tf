// GCP vars

variable "compute_instance_username" {
  default = "ubuntu"
}

variable "project" {
  default = "admin-291014"
}

variable "app_owner" {
  default = "melalex"
}

variable "region" {
  default = "us-east1"
}

variable "zone" {
  default = "us-east1-b"
}

variable "ssh_keys_folder" {
  default = ".ssh"
}

// GitHub vars

variable "github_token" {}

// Ansible vars

variable "ansible_playbook_location" {
  default = "ansible"
}

variable "ansible_inventory_file_template" {
  default = "terraform/templates/inventory.ini.tmpl"
}

variable "ansible_inventory_file" {
  default = "ansible/inventory.ini"
}

// App vars

variable "app_version" {
  default = "latest"
}

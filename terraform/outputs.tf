output "this_compute_instance_ip" {
  value = google_compute_instance.this.network_interface.0.access_config.0.nat_ip
}

output "this_local_file_ssh_private_key_pem" {
  value = local_file.private_key.filename
}

output "this_local_file_ssh_public_key_pem" {
  value = local_file.private_key.filename
}

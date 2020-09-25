output "this_compute_instance_ip" {
  value = google_compute_instance.this.network_interface.0.access_config.0.nat_ip
}

output "this_ssh_private_key_pem" {
  value = tls_private_key.this.private_key_pem
}

output "this_ssh_public_key_pem" {
  value = tls_private_key.this.public_key_pem
}

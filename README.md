variable "aws_access_key" {}
variable "aws_secret_key" {}
variable "aws_region" {}

variable "security_groups" {
  type        = "list"
}

variable "iam_instance_profile_id" {
}

variable "key_name" {
}

variable "private_subnet_ids" {
  type        = "list"
}

variable "execution_role_arn" {
}

variable "task_role_arn" {
}

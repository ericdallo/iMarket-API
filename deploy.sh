#!/bin/bash
INSTANCE_NAME=$(gcloud compute instance-groups list-instances imarket-api-group --zone us-east1-d |grep imarket-api-group | awk '{print $1}')
gcloud compute ssh $INSTANCE_NAME --zone us-east1-d --command "sudo /opt/deploy.sh"
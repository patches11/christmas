#!/bin/bash

rsync -av -e ssh --exclude='.bsp' --exclude='.idea' --exclude='project/target' --exclude='target' ../christmas miniserve:~
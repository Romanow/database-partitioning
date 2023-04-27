#!/usr/bin/env bash

set -e

psql -U postgres -d service -c "GRANT ALL PRIVILEGES ON SCHEMA public TO program;"

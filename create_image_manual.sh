#!/bin/bash
mvn clean install -DskipTests=true
docker build -t zzpj-hotelscollector .
FROM ubuntu:latest
LABEL authors="josef"

ENTRYPOINT ["top", "-b"]
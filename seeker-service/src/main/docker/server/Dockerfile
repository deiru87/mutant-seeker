FROM @alpine.image.base@
WORKDIR /
COPY maven/*.war /seeker-service.war

@entrypoint.service.seeker@
USER root

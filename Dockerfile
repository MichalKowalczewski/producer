FROM alpine:3.7
COPY . /producer

RUN apk update && apk add \
	&& apk --no-cache add openjdk11 --repository=http://dl-cdn.alpinelinux.org/alpine/edge/community \
	&& apk add maven=3.5.2-r0 \
	&& apk add bash
[![Travis (.org) branch](https://img.shields.io/travis/nl2go/plivo-sms-slack-hook/master)](https://travis-ci.org/nl2go/plivo-sms-slack-hook)
[![Codecov](https://img.shields.io/codecov/c/github/nl2go/plivo-sms-slack-hook)](https://codecov.io/gh/nl2go/plivo-sms-slack-hook)
[![Code Climate maintainability](https://img.shields.io/codeclimate/maintainability/nl2go/plivo-sms-slack-hook)](https://codeclimate.com/github/nl2go/plivo-sms-slack-hook)[
![Docker Pulls](https://img.shields.io/docker/pulls/nl2go/plivo-sms-slack-hook)](https://hub.docker.com/r/nl2go/plivo-sms-slack-hook)
[![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/nl2go/plivo-sms-slack-hook)](https://hub.docker.com/repository/docker/nl2go/plivo-sms-slack-hook/tags?page=1)

# Plivo SMS Slack Hook

[Plivo hook](https://www.plivo.com/docs/sms/guides/receive-sms#set-up-a-web-server) to receive SMS messages and send them to a Slack channel.

## Usage

    docker run -d nl2go/plivo-sms-slack-hook \
        -e PLIVO_SMS_SLACK_HOOK_WEBHOOK_URL=https://hooks.slack.com/services/foo-bar \
        -e PLIVO_SMS_SLACK_HOOK_TARGET_NUMBER=123123123 \
        -p 8080:8080

A message may be sent using `/messages` endpoint.

    curl \
        --request POST 'http://localhost:8080/messages?From=123&To=1&Text=HelloWorld' \
        --header 'Content-Type: application/x-www-form-urlencoded' \
        --data-urlencode '='
        
## Configuration
Configuration is supported through environment variables.

| Name | Description |
|------|-------------|
| `PLIVO_SMS_SLACK_HOOK_WEBHOOK_URL` | Slack incoming webhook URL. |
| `PLIVO_SMS_SLACK_HOOK_MESSAGE_TEMPLATE` | Slack webhook message template. |
| `PLIVO_SMS_SLACK_HOOK_TARGET_NUMBER` | Plivo managed phone number that is capable of receiving the SMS. Used as authorization verification. |

Defaults may be found within [application.yml](src/main/resources/application.yml).

## Prerequisites

- OpenJDK 11
- Docker >=17.x
- Docker Compose >=2.3

## Development

    $ mvn clean package
    $ docker-compose up -d

## Maintainers

- [build-failure](https://github.com/build-failure)

## License

See the [LICENSE.md](LICENSE.md) file for details

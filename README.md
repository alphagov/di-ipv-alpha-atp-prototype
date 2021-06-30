# Generic ATP Service

A template for an ATP service.

## Environment Variables

This service requires the following variables set in the environment,

| variable name | description |
|-|-|
| `DI_IPV_GENERIC_ATP_SIGN_KEY` | The signing key used to sign the JWS sent to the IPV. |
| `DI_IPV_GENERIC_ATP_SIGN_CERT` | The signing certificate used for IPV to verify signature. |

## What this does

At the moment, all this template does is takes the data,
and wraps it in a JWS signed with the ATPs signing key.

The certificate can either be pinned to the consuming service
or a `/.well-known/jwks.json` could be set up on the ATP to
verify the signature with the certificate / public key.
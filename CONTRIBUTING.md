# Contribution Guide

We'd love to accept patches and contributions to this project.
Please, follow these guidelines to make the contribution process easy and effective for everyone involved.

## Contributor License Agreement

You must sign the [Contributor License Agreement](https://github.com/Netcracker/qubership-workflow-hub/blob/main/CLA/cla.md) in order to contribute.

## Code of Conduct

Please make sure to read and follow the [Code of Conduct](CODE-OF-CONDUCT.md).

## Branches and releases

`main` is the upgraded line (Spring Boot 3.5.x, `v2.x` tags). Production can still run an earlier release from before
the upgrade (`v1.0.x` tags). If a defect is reported against such a release, a second long-lived branch is needed,
as in the two repositories below, where `old-main-before-springboot-upgrade` exists next to `main`.

- [itf-executor](https://github.com/Netcracker/qubership-testing-platform-itf-executor)
- [diameter-transport-library](https://github.com/Netcracker/qubership-testing-platform-diameter-transport-library)

This repository does not have that branch yet, so `main` is its only line.

When a defect is reported against a `v1.0.x` release:

1. Create `old-main-before-springboot-upgrade` from the commit of the last `v1.0.x` release tag, and push it. Ask
   a maintainer to protect it the way `main` is protected.
2. Create your fix branch from `old-main-before-springboot-upgrade` and open the pull request against it.
3. Never merge `old-main-before-springboot-upgrade` into `main`, and never merge `main` into it. When a fix is needed
   in both lines, open two pull requests, and port the second one by cherry-picking the first and adapting it to the
   Spring Boot differences.
4. Release from the branch that the fix is on. Start the `Maven release` workflow (`maven-release-v2.yaml`) with
   `Use workflow from` set to that branch. A release from `old-main-before-springboot-upgrade` continues the `v1.0.x`
   tags.
5. Keep dependencies inside one line: on `old-main-before-springboot-upgrade`, depend on the pre-upgrade releases of
   the other ITF services and libraries.
6. Once the branch exists, copy this section and `AGENTS.md` to it, and rewrite this section as in the two
   repositories named above, so that both branches describe the layout as it is.

Defects reported against `v2.x` releases, and new features, go to `main` as usual.

# Instructions for AI agents

## Possible second release line: read this before you change code

`main` is the upgraded line (Spring Boot 3.5.x, release tags `v2.x`). Production may still run an earlier release
from before the upgrade (`v1.0.x`). This repository has no `old-main-before-springboot-upgrade` branch at the moment,
so `main` is its only line.

If a task concerns a defect in a `v1.0.x` release, do not fix it on `main`. Stop and ask whether to create
`old-main-before-springboot-upgrade` from the last `v1.0.x` release tag, as in the repositories below.

- [itf-executor](https://github.com/Netcracker/qubership-testing-platform-itf-executor)
- [diameter-transport-library](https://github.com/Netcracker/qubership-testing-platform-diameter-transport-library)

When that branch exists:

1. Cut the fix branch from it and target the pull request at it.
2. Never merge or rebase `main` and `old-main-before-springboot-upgrade` into each other. If both lines need the fix,
   make two pull requests and port the second by cherry-picking.
3. Do not use Spring Boot 3.5 APIs or dependency versions on the old branch.
4. Release from the branch of the fix.

Details are in [CONTRIBUTING.md](CONTRIBUTING.md#branches-and-releases).

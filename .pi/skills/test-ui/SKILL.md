---
name: test-ui
description: Runs fail-fast, adversarial text-UI tests from command/input and expected-output lists, records cases in test/ui-test-plan.md, and shows console transcripts. Use when testing this project's command-line UI or when invoked with /skill:test-ui.
compatibility: Requires Bash, Python 3, and Java.
---

# Test UI

Test Rambo's text UI from the repository root. Treat user input as hostile and try to make the UI crash, hang, corrupt state, or show a misleading response.

## Source of truth

Use `test/ui-test-plan.md`. If the user supplies test cases, commands, or expected outputs, record them there before testing. Never replace expected output with actual output merely to make a test pass.

Each case must contain:

- aim;
- fresh-process launch command (or the plan's default);
- input, exactly as lines sent to standard input;
- expected output;
- match mode: `exact` or `ordered`;
- expected exit status (normally `0`).

`exact` compares all output byte-for-byte. `ordered` requires every expected literal to occur in order and permits other output between them. Prefer `exact`; use `ordered` for long shared menus or banners.

## Procedure

1. Read the complete plan and relevant UI code. Add adversarial cases that are missing, but do not invent product requirements: blank input, whitespace, EOF, invalid menu choices, malformed and extreme values, repeated operations, boundary indexes, long/non-ASCII text, command-like text, and stateful sequences.
2. Record the version reported by `java -version`.
3. Run the plan's build command once. Stop if it fails.
4. Run cases in listed order, starting a fresh process for every case. Feed the listed input through standard input, merge stderr into stdout, capture the exit status, and enforce the timeout. Store temporary captures outside the repository.
5. Compare the captured output and exit status with the case's expectations. Use `cmp` for `exact`. For `ordered`, use a short Python standard-library check based on successive `str.find` calls; do not use regexes or visual judgment.
6. **Fail fast:** on the first timeout, exit-status mismatch, or output mismatch, run no more cases. Show the failed case, the annotated console session, expected output, actual output, and the first missing/differing portion when available.
7. If all cases pass, show an annotated console session for every case and a concise pass summary.
8. Update the plan's Results section with the date, Java version, cases run, and PASS/FAIL. Do not mark unrun cases as passed.

## Console record

Display every tested session in this form; preserve blank lines and spaces inside fenced blocks:

```text
$ <launch command>
<input 1
<input 2
--- output ---
<captured stdout and stderr>
--- exit: 0 ---
```

The `<` prefix is annotation only and is not sent to the program. Keep the raw captured output unchanged in the output section.

## Safety

Use a per-case timeout (default 10 seconds). Do not run destructive shell input as shell commands: test-case inputs go only to the program's standard input. Do not modify application code during a test session; report the first failure instead.

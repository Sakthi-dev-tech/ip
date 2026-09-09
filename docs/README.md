# Rambo User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Listing and searching for tasks

Enter `3` to list all saved tasks:

```text
3
```

Add a keyword after `3` to show only tasks whose descriptions contain that
keyword. Searching is case-insensitive and also supports phrases:

```text
3 milk
3 buy milk
```

Search results retain their original task numbers so you can use those numbers
when toggling or deleting tasks.

## Assigning priorities

Use `priority` to assign a level from 1 to 3 to an existing task. Level 1 is the
highest priority and level 3 is the lowest:

```text
priority TASK_NUMBER LEVEL
```

For example, this marks task 2 as highest priority:

```text
priority 2 1
```

In the terminal menu, choose option `6` and enter the task number and priority
level when prompted.

The assigned level appears beside the task's type and completion status, such as
`[T][][P1] buy milk`. Assigning another level replaces the current priority.
Priorities do not change the order of the task list.

## Feature XYZ

// Feature details

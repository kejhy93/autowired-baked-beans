# Instructions for coding agents

This repo teaches Spring beans one stage at a time, with one Maven module per stage. See `LEARNING_PLAN.md` for the stages. The code and docs are both learning material, so a wrong doc is a bug.

## After changing source code

Source code means anything under `stage-*/src/`, plus any `pom.xml`. Do these steps before you report the work as done.

### 1. Check the code

Run the same build as CI from the repo root. It needs JDK 25.

```bash
mvn -B package
```

If it fails, fix the cause and run it again. Then read your full diff for bugs and unintended changes. If you changed behavior, add or update a test.

### 2. Decide whether the change is notable

A change is notable if someone following the docs would now see, run, or learn something different.

| Notable | Not notable |
| --- | --- |
| A class, bean, package, or variant is added, removed, or renamed | Formatting or comment-only changes |
| Wiring or behavior changes | Refactors that keep names and behavior the same |
| Program output changes | Tests that don't change what the docs claim |
| Tests are added or removed, because the site shows the count | Patch or minor dependency bumps |
| Run commands or the required JDK change | Build plugin bumps |
| A major Spring or JDK version bump | |
| A new stage module | |

If you're unsure, treat the change as notable. If it isn't notable, skip the remaining steps and say so in your summary.

### 3. Update the READMEs

- **`stage-NN-*/README.md`:** this is the main guide for a stage. Update every section the change affects, including commands, the sample output, and the key terms.
- **Root `README.md`:** this is a short project description. Update it only if the description is no longer accurate.
- **`LEARNING_PLAN.md`:** update it only if the scope of a stage changes.

### 4. Update the GitHub Pages site

The site is the hand-written `docs/index.html`, served from `docs/`. It currently covers Stage 1 only. If you add a stage, ask the maintainer how it should appear on the site.

Update each of these that the change affects:

- **Prose and experiments:** the sections that describe the code, including anything that quotes a README.
- **Facts in the page header:** the Spring version, JDK version, and number of passing tests.
- **Run commands:** the `#run` section.
- **Embedded sources:** the JSON in `<script id="sources">` holds a copy of each file in the code viewer. Refresh every entry, and add or remove entries when files change. Each value must match its file exactly, and `<` must be written as `<`.
- **File references:** the `data-file` attributes and any `showFile(...)` calls must point to paths that exist in the embedded sources.

Then confirm that the JSON still parses and every embedded value matches its file, and open the page in a browser if you can.

The `REPO` constant in the page script pins the source links to the commit the embedded sources came from. Pull requests are squash-merged, so a branch commit's SHA won't last. Leave `REPO` unchanged in the pull request and note that it needs a follow-up. After the merge, set `REPO` to the merge commit's full SHA in a separate pull request.

Non-notable changes leave the site alone. The embedded sources stay a consistent snapshot of the pinned commit, and the next notable change refreshes them.

### 5. Report what you did

In your summary, state the build result, whether the change was notable and why, and which docs you updated.

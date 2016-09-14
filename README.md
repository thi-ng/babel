# thing-babel

A Leiningen project template for literate Clojure & Clojurescript
projects w/ Emacs & [org-mode](http://orgmode.org).

This template does *not* create a working Lein project directory per
se, but constitutes more of a meta Lein template consisting of a
several `.org` files (most importantly
[readme.org](./src/leiningen/new/thing_babel/readme.org)) which
contains both a structured template for a library README and a
parametric description of the Lein project to be generated and
regenerated in the future. These `.org` files must be tangled with the
supplied `tangle.sh` script and will generate a working lein project
directory from those.

## 0.3.2.2 - 2015-08-30

* Bugfix readme tpl filename case issue (#2)

## 0.3.2 - 2015-08-29

* Bugfix generated file paths (see #2, #3)

## 0.3.1 - 2015-08-28

* Update dependencies

## 0.3.0 - 2015-08-14

* Update dependencies to latest stable versions
* Remove CLJX, add reader conditionals
* Restructure project layout (move `src/index.org` => `readme.org` in main dir)
* Restructure `readme.org` internal structure
* Add TOC headings (requires [org-toc package](https://github.com/snosov1/org-toc)
* Add `tangle-all.sh` bash script

## 0.2.3

* Update included dependencies (see below)
* Add support for MIT license
* Change internal project ORG-links to be GitHub friendly
* Minor cleanups of template files

## 0.2.0

* Generated `.org` files are now placed in dedicated `src` & `test`
  sub folders to provide a cleaner project structure.
* A `setup.org` file is created to define common & shared org-mode
  configuration for all project files.

New in version 0.2.0 is also the presence of
[libraryofbabel.org](./src/leiningen/new/thing_babel/libraryofbabel.org),
org-mode's mechanism to support re-usable & parametric code templates.
The generated `.org` file for the main namespace demonstrates the use
of such code templates (albeit in a very construed way). A much better
and more realistic use for these templates is to provide skeleton
implementations when working with Clojure protocols. See
[thi.ng/geom](http://thi.ng/geom) for a concrete example.

## Objective

The template is aimed at an x-platform CLJX project structure and
configures a Lein project with the currently latest versions of:

* Clojure 1.7.0
* ClojureScript 1.7.107
* cljsbuild 1.1.0
* clojurescript.test 0.3.3
* criterium 0.4.3

## Usage

The project name given can be fully qualified (as in the example
below), however the created directory will only take the last name part
minus the leading group ID (if any). The fully qualified name is used
to define namespaces & target paths within the generated .org files
and will also be inserted into the resulting `project.clj` file.

```bash
lein new thing-babel org.foo/bar \
  author "my name" \
  email "a@b.com" \
  url "http://project.com" \
  author-url "http://home.com" \
  license ASL \
  target generated \
  desc "Another great project"

Generating fresh literate programming project: org.foo/bar
generated project dir   : bar
artefact group ID       : org.foo
project url             : http://project.com
project author          : my name
author url              : http://home.com
author email            : a@b.com
license                 : Apache Software License 2.0
description             : Another great project
path for gen sources    : generated/
project root namespace  : org.foo.bar

# switch into newly created project folder
cd bar

# (re)generate actual lein project & sources
./tangle-all.sh 

# switch into generated project (value of `target` key above)
cd generated

# trigger cleaning, cljx processing & testing
lein test org.foo.bar.test.core

FAIL in (epic-fail) (core.clj:12)
FIXME
expected: (= 3 (+ 1 1))
  actual: (not (= 3 2))

Ran 1 tests containing 1 assertions.
1 failures, 0 errors.
Tests failed.
```

Success!

Apart from the project name all other options are optional (indeed).
If omitted, some values will assume the following defaults:

* **author** - local user name (via `(System/getProperty
  "user.name")`)
* **desc** - `"FIXME: ..."` (project description)
* **license** - `EPL` (lein's default choice, currently only other
  choices are `ASL` or `MIT`)
* **target** - `babel` (source folder prefix for generated/tangled
  source blocks from .org files)
* **url** - `"http://github.com"` (project url, must be quoted)

## Misc

I recommend using the Emacs
[Leuven theme](https://github.com/fniessen/emacs-leuven-theme) for a
great org-mode experience...

In order to automatically update the TOC of each org file, I too
recommend installing the
[toc-org package](https://github.com/snosov1/toc-org) Emacs package.

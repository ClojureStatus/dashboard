---
title: Clojure Project Status
description: Clojure community projects status overview.
---

<style>
table {
border:0 none;
width:100%;
}
th,td {
border:0 none;
padding:0.3rem 0 0.3rem;
text-align:left;
}
</style>


## Project Status

<table>
  <thead>
    <tr>
    <th>Name</th>
    <th>Repo</th>
    <th>Build</th>
    <th>v1.8</th>
    <th>v1.9</th></tr>
  </thead>

  <tbody>
  {% assign projects = site.data.projects | sort: 'name' %}
  {% for project in projects %}
    {% if project.name %}
    <tr>
    <td>{{ project.name }}</td>
    <td><a href="{{ project.repo }}">{{ project.repo }}</a></td>
    <td><img src="{{ project.badge }}" alt="{{project.name}} status badge"/></td>
    <td>{% if project.v18 %}&#10004;{% endif %}</td>
    <td>{% if project.v19 %}&#10004;{% endif %}</td>
    </tr>
    {% endif %}
  {% endfor %}
  </tbody>
</table>

## Getting Involved

You can add your project to the status board by making a PR to
[ClojureStatus/dashboard](https://github.com/ClojureStatus/dashboard). By making
a PR it is assumed you accept your contribution will be placed in the public
domain.

To get started add a record to `_data/projects.json` followed by an empty
newline (this helps with merging).

If you want to verify the project locally you'll need the following
dependencies;

 * Ruby 2.x (personally used 2.3.7p456 on OS X).
 * Bundler (`gem install bundler`).

Once they're installed you can run the following commands from the command line
in this projects root folder;

```shell
bundle install --path vendor/bundle --binstubs=bin
./bin/jekyll s
```

The current fields are as follows;

<dl>
<dt>name</dt>
<dd>Your projects name.</dd>
<dt>badge</dt>
<dd>Your projects build badge image URL.</dd>
<dt>repo</dt>
<dd>Your projects repository URL.</dd>
<dt>v18</dt>
<dd>Indicates your project is Clojure v1.8 compatible.</dd>
<dt>v19</dt>
<dd>Indicates your project is Clojure v1.9 compatible.</dd>
</dl>

## Periodic Builds

In order for the build statuses to be useful we want a periodic build against a
Clojure SNAPSHOT build. Documentation is underway as to how best approach this.
Collaboration with Cognitect on how best to generate SNAPSHOTS that can be
consumed with community projects is also required.

Below is documentation for scheduling builds on various SaaS CI services;

 * [CircleCI](https://support.circleci.com/hc/en-us/articles/115015481128-Scheduling-jobs-cron-for-builds-)
 * [TravisCI](https://docs.travis-ci.com/user/cron-jobs/)


@PLUGIN@ Configuration
======================

By configuration it is possible to specify which actions should be removed.

The configuration must be done in the `gerrit.config` of the Gerrit
server.

### <a id="hide-actions">Section hide-actions</a>

```
[plugin "hide-actions"]
  actionToHide = move
  actionToHide = rebase
```

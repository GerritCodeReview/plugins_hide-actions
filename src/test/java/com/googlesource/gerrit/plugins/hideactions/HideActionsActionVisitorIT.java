// Copyright (C) 2017 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.hideactions;

import static com.google.common.truth.Truth.assertThat;

import com.google.gerrit.acceptance.GerritConfig;
import com.google.gerrit.acceptance.LightweightPluginDaemonTest;
import com.google.gerrit.acceptance.PushOneCommit;
import com.google.gerrit.acceptance.TestPlugin;
import com.google.gerrit.extensions.common.ActionInfo;
import java.util.Map;
import org.junit.Test;

/** Test the hide-actions plugin. */
@TestPlugin(
  name = "hide-actions",
  sysModule = "com.googlesource.gerrit.plugins.hideactions.HideActionsActionVisitor$Module"
)
public class HideActionsActionVisitorIT extends LightweightPluginDaemonTest {

  @Test
  public void defaultActions() throws Exception {
    PushOneCommit.Result r = createChangeWithTopic();
    r.assertOkStatus();
    Map<String, ActionInfo> actions = getActions(r.getChangeId());
    assertThat(actions).hasSize(3);
    assertThat(actions.keySet()).containsExactly("cherrypick", "rebase", "description");
  }

  @Test
  @GerritConfig(
    name = "plugin.hide-actions.actionToHide",
    values = {"rebase", "cherrypick"}
  )
  public void removedAction() throws Exception {
    PushOneCommit.Result r = createChangeWithTopic();
    r.assertOkStatus();
    Map<String, ActionInfo> actions = getActions(r.getChangeId());
    assertThat(actions).hasSize(1);
    assertThat(actions.keySet()).containsExactly("description");
  }

  private PushOneCommit.Result createChangeWithTopic() throws Exception {
    return pushFactory
        .create(db, admin.getIdent(), testRepo, "a message", "a.txt", "content\n")
        .to("refs/for/master/" + name("topic"));
  }
}

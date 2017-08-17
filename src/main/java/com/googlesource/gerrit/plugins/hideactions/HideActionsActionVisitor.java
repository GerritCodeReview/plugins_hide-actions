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

import com.google.common.collect.ImmutableList;
import com.google.gerrit.extensions.annotations.PluginName;
import com.google.gerrit.extensions.api.changes.ActionVisitor;
import com.google.gerrit.extensions.common.ActionInfo;
import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.RevisionInfo;
import com.google.gerrit.extensions.registration.DynamicSet;
import com.google.gerrit.server.config.PluginConfig;
import com.google.gerrit.server.config.PluginConfigFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;

public class HideActionsActionVisitor implements ActionVisitor {

  public static class Module extends AbstractModule {
    @Override
    protected void configure() {
      DynamicSet.bind(binder(), ActionVisitor.class).to(HideActionsActionVisitor.class);
    }
  }

  private static final String ACTIONS_TO_HIDE_KEY = "actionToHide";

  private final ImmutableList<String> actionsToHide;

  @Inject
  HideActionsActionVisitor(PluginConfigFactory pluginConfigFactory, @PluginName String pluginName) {
    PluginConfig pCfg = pluginConfigFactory.getFromGerritConfig(pluginName, true);
    actionsToHide = ImmutableList.copyOf(pCfg.getStringList(ACTIONS_TO_HIDE_KEY));
  }

  @Override
  public boolean visit(String name, ActionInfo actionInfo, ChangeInfo changeInfo) {
    return !actionsToHide.contains(name);
  }

  @Override
  public boolean visit(
      String name, ActionInfo actionInfo, ChangeInfo changeInfo, RevisionInfo revisionInfo) {
    return !actionsToHide.contains(name);
  }
}

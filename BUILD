load("//tools/bzl:junit.bzl", "junit_tests")
load("//tools/bzl:plugin.bzl", "gerrit_plugin", "PLUGIN_DEPS", "PLUGIN_TEST_DEPS")

gerrit_plugin(
    name = "hide-actions",
    srcs = glob(["src/main/java/**/*.java"]),
    manifest_entries = [
        "Gerrit-PluginName: hide-actions",
        "Gerrit-Module: com.googlesource.gerrit.plugins.hideactions.HideActionsActionVisitor$Module",
    ],
    resources = glob(["src/main/resources/**/*"]),
    deps = PLUGIN_DEPS,
)

junit_tests(
    name = "hide_actions_tests",
    srcs = glob(["src/test/java/**/*IT.java"]),
    tags = ["hide-actions"],
    visibility = ["//visibility:public"],
    deps = PLUGIN_DEPS + PLUGIN_TEST_DEPS + [
        ":hide-actions__plugin",
    ],
)

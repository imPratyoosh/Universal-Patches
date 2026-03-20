package app.template.patches.hideicon

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch

private const val EXTENSION_CLASS_DESCRIPTOR = "Lapp/template/extension/HideAppIcon;"

@Suppress("unused")
val hideAppIconPatch = bytecodePatch(
    name = "Hide App Icon",
    description = "Hide the app icon from the launcher for any application."
) {
    compatibleWith("*") // Compatible with all apps

    extendWith("extensions/hideappicon.mpp")

    // Business logic to hide app icon from launcher
    execute {
        LauncherActivityFingerprint.method.addInstructions(
            0,
            """
                invoke-static {}, $EXTENSION_CLASS_DESCRIPTOR;->shouldHideIcon()Z
                move-result v0
                if-eqz v0, :show_icon
                const/4 v0, 0x0
                return v0
                :show_icon
                nop
            """
        )
    }
}
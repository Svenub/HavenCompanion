package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState


/**
 * A composable function that displays a dialog to request microphone permissions from the user.
 * It handles various states of the permission request, such as showing rationale or handling permanent denial.
 *
 * @param showDialog A lambda that is invoked to control the visibility of the dialog. It provides a boolean indicating if the dialog should be shown.
 * @param permissionState The [PermissionState] that represents the current state of the microphone permission.
 * @param toggleVoiceActivation A lambda that is invoked when the microphone permission is granted. It provides a boolean indicating if voice activation should be toggled on.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MicrophonePermissionDialog(
    showDialog: (Boolean) -> Unit,
    permissionState: PermissionState,
    toggleVoiceActivation: (Boolean) -> Unit
) {
    if (!permissionState.hasPermission && !permissionState.permissionRequested && !permissionState.shouldShowRationale) {

        SideEffect {
            permissionState.launchPermissionRequest()
        }

    } else if (!permissionState.hasPermission && permissionState.shouldShowRationale) {
        AlertDialog(
            onDismissRequest = { showDialog(false) },
            title = { Text("Microphone Permission Required") },
            text = { Text("Microphone permission is needed to use the speech-recogniser") },
            confirmButton = {
                Button(onClick = {
                    permissionState.launchPermissionRequest()
                }) {
                    Text("OK")
                }
            }
        )
    } else if (!permissionState.hasPermission && !permissionState.shouldShowRationale) {
        AlertDialog(
            onDismissRequest = { showDialog(false) },
            title = { Text("Microphone Permission Required") },
            text = { Text("Microphone permission was permanently denied. You can enable it in the app settings") },
            confirmButton = {
                Button(onClick = {
                     showDialog(false)
                }) {
                    Text("Ok")
                }
            }
        )
    } else if(permissionState.hasPermission) {
        toggleVoiceActivation(true)
    }

}


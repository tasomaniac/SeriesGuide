/*
 * Copyright 2014 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.battlelancer.seriesguide.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * A dialog displaying a list of options to choose from, saving the selected option to the given
 * preference upon selection by the user.
 */
public class SingleChoiceDialogFragment extends DialogFragment {

    public static SingleChoiceDialogFragment newInstance(int itemArrayResource,
            int itemDataArrayResource, int selectedItemIndex, String preferenceKey,
            int dialogTitleResource) {
        SingleChoiceDialogFragment f = new SingleChoiceDialogFragment();

        Bundle args = new Bundle();
        args.putInt("itemarray", itemArrayResource);
        args.putInt("itemdata", itemDataArrayResource);
        args.putInt("selected", selectedItemIndex);
        args.putString("prefkey", preferenceKey);
        args.putInt("dialogtitle", dialogTitleResource);
        f.setArguments(args);

        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final CharSequence[] items = getResources().getStringArray(
                getArguments().getInt("itemarray"));

        return new AlertDialog.Builder(getActivity())
                .setTitle(getString(getArguments().getInt("dialogtitle")))
                .setSingleChoiceItems(items, getArguments().getInt("selected"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                final SharedPreferences.Editor editor = PreferenceManager
                                        .getDefaultSharedPreferences(getActivity()).edit();
                                editor.putString(
                                        getArguments().getString("prefkey"),
                                        (getResources().getStringArray(getArguments().getInt(
                                                "itemdata")))[item]);
                                editor.apply();
                                dismiss();
                            }
                        }).create();
    }
}

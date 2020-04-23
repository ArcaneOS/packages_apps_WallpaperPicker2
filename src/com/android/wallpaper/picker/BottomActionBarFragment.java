/*
 * Copyright (C) 2020 The Android Open Source Project
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
package com.android.wallpaper.picker;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.wallpaper.widget.BottomActionBar;
import com.android.wallpaper.widget.BottomActionBar.BottomActionBarHost;

/**
 * Base class for Fragments that own a {@link BottomActionBar} widget.
 *
 * A Fragment extending this class is expected to have a {@link BottomActionBar} in its activity
 * which is a {@link BottomActionBarHost}, which can handle lifecycle management of
 * {@link BottomActionBar} for extending fragment.
 */
public class BottomActionBarFragment extends Fragment {

    private BottomActionBar mBottomActionBar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBottomActionBar = ((BottomActionBarHost) getActivity()).getBottomActionBar();
        mBottomActionBar.bindCancelButtonToBackKey(getActivity());
        onBottomActionBarReady(mBottomActionBar);
    }

    @Override
    public void onDestroyView() {
        if (mBottomActionBar != null) {
            mBottomActionBar.reset();
            mBottomActionBar = null;
        }
        super.onDestroyView();
    }

    /** Returns {@code true} if the fragment would handle the event. */
    public boolean onBackPressed() {
        if (mBottomActionBar != null && mBottomActionBar.isVisible()) {
            mBottomActionBar.hide();
            return true;
        }
        return false;
    }

    /**
     * Gets called when {@link #onViewCreated} finished. For extending fragment, this is the only
     * one interface to get {@link BottomActionBar}.
     */
    protected void onBottomActionBarReady(BottomActionBar bottomActionBar) {}
}

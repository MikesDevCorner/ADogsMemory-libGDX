package com.xsheetgames.memory;

import com.xsheetgames.memory.interfaces.NativeFunctions;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.xsheetgames.memory.MemoryGdxGame;

import java.lang.annotation.Native;

public class IOSLauncher extends IOSApplication.Delegate implements NativeFunctions {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new MemoryGdxGame(this), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    @Override
    public void showMessage(String title, String message) {

    }

    @Override
    public void openURL(String url) {

    }

    @Override
    public void share(String subject, String text) {

    }

    @Override
    public void rate() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void trackPageView(String path) {

    }

    @Override
    public void sendException(String description, boolean fatal) {

    }

    @Override
    public void sendEvent(String category, String subCategory, String component, long value) {

    }

    @Override
    public void showFullScreenAd() {

    }

    @Override
    public void showBannerAd() {

    }

    @Override
    public void closeBannerAd() {

    }

    @Override
    public void TriggerStandingInterstitials() {

    }

}
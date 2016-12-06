# DashPlayer

Android Exercise

Contains:

1. dashsdk: Android Library which is wrap around ExoPlayer. This is a short version of the ExoPlayer demo project (Can be shorten more if needed)


   Usage Example:

        DashPlayer player = new DashPlayer(getApplicationContext(),  "DashPlayer", contentUri.toString());
        player.seekTo(0);
        player.prepare();
        player.setSurface(surfaceView.getHolder().getSurface());
        player.start();

2. app: Android application which shows an example using dashsdk.

        There are 2 activities: MainActivity and Player.




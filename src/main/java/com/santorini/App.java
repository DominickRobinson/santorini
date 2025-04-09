package com.santorini;

public class App 
{
    private static final int SERVER_PORT = 8080;

    public static void main( String[] args )
    {
        try {
            new GameServer(SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

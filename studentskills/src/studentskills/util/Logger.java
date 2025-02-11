package studentskills.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Logger {
    protected static Logger instance = null;
    protected final Level level;
    protected final Set<Level> logtoFileForLevel;
    protected final FileWriter fw;

    public Logger(Level levelIn, String logFileIn, Level... logtoFileForLevelIn) throws IOException{
        if (instance != null) {
            throw new RuntimeException("Logger already initialized, cannot initialize it again");
        }
        level = levelIn;
        fw = new FileWriter(logFileIn);
        logtoFileForLevel = new HashSet<>(Arrays.asList(logtoFileForLevelIn));
    }

    public static Logger getInstance(){
        if(Logger.instance == null){
            throw new RuntimeException("Logger not initialized. Initialize it using the constructor new Logger(...)");
        }
        return Logger.instance;
    }

    public void log(Level levelIn, String msgIn, Object ...argsIn){
        if (levelIn.toInt() <= level.toInt()) {
            String logMsg = String.format("[%s][%s] %s %s", new Date(), levelIn, msgIn, (argsIn == null || argsIn.length == 0) ? "" : "(" + (argsIn.length == 1 ? argsIn[0] : argsIn) + ")");
            if (levelIn == Level.ERROR){
				System.err.println(logMsg);
            }
            else{
                System.out.println(logMsg);
            }
            if(logtoFileForLevel == null || logtoFileForLevel.contains(levelIn)){
                try {
                    fw.write(logMsg + "\n");
                    fw.flush();
                } catch (IOException e) {
                    System.err.println("Failed to write to log file");
					e.printStackTrace();
                }
            }
        }
    }
    
    public static void debugHigh(String msgIn, Object... argsIn) {
		Logger.getInstance().log(Level.DEBUG_HIGH, msgIn, argsIn);
	}

	public static void debugMed(String msgIn, Object... argsIn) {
		Logger.getInstance().log(Level.DEBUG_MED, msgIn, argsIn);
	}

	public static void debugLow(String msgIn, Object... argsIn) {
		Logger.getInstance().log(Level.DEBUG_LOW, msgIn, argsIn);
	}

	public static void config(String msgIn, Object... argsIn) {
		Logger.getInstance().log(Level.CONFIG, msgIn, argsIn);
	}

	public static void info(String msgIn, Object... argsIn) {
		Logger.getInstance().log(Level.INFO, msgIn, argsIn);
	}

	public static void warn(String msgIn, Object... argsIn) {
		Logger.getInstance().log(Level.WARN, msgIn, argsIn);
	}

    public void close(){
        try {
            if (fw != null) {
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeLogger(){
        getInstance().close();
    }


    public static enum Level{
        ERROR(1), WARN(2), INFO(3), CONFIG(4), DEBUG_LOW(5), DEBUG_MED(6), DEBUG_HIGH(7),; 
        private int levelNum;
        Level(int levelNumIn){
            levelNum = levelNumIn;
        }
        public int toInt(){
            return levelNum;
        }

        public static Level from(int levelInt){
            for(Level level : Level.values()){
                if (level.toInt() == levelInt) {
                    return level;
                }
            }
            throw new RuntimeException("Invalid log level number: [" + levelInt + "]");
        }
    }
}

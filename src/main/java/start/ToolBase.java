package start;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.MySQLConnection;

import octopus.utils.OctopusExcelDataTool;
import octopus.utils.RemoteLogger;

public class ToolBase extends OctopusExcelDataTool {
	public Map<String, String> PUBLIC_CONFIG;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public static String param(String paramName) {
		return env.get(paramName);
	}

	public static String param(String paramName, String defValue) {
		String result = env.get(paramName);
		if (result == null)
			result = defValue;
		return result;
	}

	private static Map<String, String> env = null;
	static {
		env = new HashMap<String, String>();
		env.putAll(System.getenv());
	}

	public MySQLConnection dbConn;
	public static RemoteLogger logger = new RemoteLogger("http://Localhost/DebugYang/log", "ReceiveLog.pas|@OnHandleStrData", "DebuggerYang");

	public void initDB(String url, String user, String password) {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			dbConn = (MySQLConnection) DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/***
	* Note: for using this function, please make sure that the inner class and
	* the corresponding instance variable MUST reside in the same outer class.
	* @param annotation
	*/
	public void instanciateFieldsWithAnnotation(Class<? extends Annotation> annotation) {
		Class<?> clazz = getClass();
		while (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				try {
					if (field.isAnnotationPresent(annotation)) {
						Class<?> fieldType = field.getType();
						Constructor<?> constructor = null;
						try {
							constructor = fieldType.getConstructor(clazz);
							field.set(this, constructor.newInstance(this));
						} catch (NoSuchMethodException e2) {
							try {
								constructor = fieldType.getConstructor(clazz, annotation);
								field.set(this, constructor.newInstance(field.getAnnotation(annotation)));
							} catch (NoSuchMethodException e) {
								field.set(this, fieldType.newInstance());
							}
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
			clazz = clazz.getSuperclass();
		}
	}

	public static class Profiler {
		long startTick;
		long lastTick;

		public void startProfiling(String blockName) {
			System.out.println("=============================================");
			System.out.println("====>> Profiling of [" + blockName + "] started!");
			startTick = System.currentTimeMillis();
			lastTick = startTick;
		}

		public void logPoint(String name) {
			long currTick = System.currentTimeMillis();
			System.out.println("====>> " + (currTick - lastTick) + " : [" + name + "]");
			lastTick = currTick;
		}

		public void endProfiling() {
			logPoint("END");
			System.out.println("====>> Profiling finished, total " + (System.currentTimeMillis() - startTick) + "ms");
			System.out.println("=============================================");
			startTick = System.currentTimeMillis();
			lastTick = startTick;
		}
	}

	public static Profiler _____p = new Profiler();
}

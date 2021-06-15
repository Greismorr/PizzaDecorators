package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import javax.swing.JButton;

public class Main {
	public static URL[] loadJars(Hashtable<String, String> decoratorNames){
		final String pluginsPath = "./plugins/";
		File pluginDir = new File(pluginsPath);
	  	String[] plugins = pluginDir.list();
	  	URL[] jars = new URL[plugins.length];
	  	
	  	for(int i = 0; i < plugins.length; i++){
	  		try{		  		
				jars[i] = (new File(pluginsPath + plugins[i])).toURI().toURL();
		  		String pathToString = jars[i].toString();
		  		String pluginName = pathToString.substring(pathToString.lastIndexOf('/') + 1).replace(".jar", "");
		  		String pluginPackage = pluginName.toLowerCase().replace("decorator", "Decorator");
		  		
		  		if(pluginName.contains("Decorator")) {
			  		decoratorNames.put(pluginName, pluginPackage);
		  		}
			}catch(MalformedURLException error){
				error.printStackTrace();
			}
	  	}
	  	
	  	return jars;
	}
	
	private static final Class[] parameters = new Class[]{URL.class};
	
	public static URLClassLoader getSysLoader(URL[] jars) {
		URLClassLoader sysLoader = null;
		
		try {	
			sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Class sysClass = URLClassLoader.class;
			final String addUrlMethod = "addURL";
			Method method = sysClass.getDeclaredMethod(addUrlMethod, parameters);
			
			method.setAccessible(true);
			
			for(URL jar : jars) {
				method.invoke(sysLoader, new Object[]{jar});
				String jarString = jar.toString();
				String jarName = jarString.substring(jarString.lastIndexOf("/") + 1);
				System.out.println(jarName + " loaded successfully!");
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysLoader;
	}
	
    public static void main(String[] args) {
    	try {
	    	Hashtable<String, String> decoratorNames = new Hashtable<String, String>();
	    	URL[] jars = loadJars(decoratorNames);
			URLClassLoader sysLoader = getSysLoader(jars);
			final String windowClassName = "window.MainWindow";
			final String createWindowMethod = "createWindow";
			Class mainWindowClass = Class.forName(windowClassName);
			Method createWindow = mainWindowClass.getMethod(createWindowMethod, Collection.class, ActionListener.class);
			
			
			ActionListener prepareListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
		        		Collection<String> decorators = (Collection<String>) (((JButton)e.getSource()).getClientProperty( "selectedDecoratorsNames"));
		        		ArrayList<Constructor> decoratorConstructors = new ArrayList();
		        		final String interfaceClassName = "interfaces.PizzaComponent";
		        		final String pizzaSimplesClassName = "concreteComponent.PizzaSimples";
		        		final String prepareMethod = "preparar";
		        		Class interfaceClass = Class.forName(interfaceClassName, true, sysLoader);;
		        		Constructor pizzaSimplesConstructor = Class.forName(pizzaSimplesClassName, true, sysLoader).getConstructor();
		        		Method interfaceMethodPrepare = interfaceClass.getDeclaredMethod(prepareMethod);
		        		
		        		
			    		for(String decorator: decorators) {
		        			Constructor decoratorConstructor;
		        			Class metaFactory = null;
		    				String decoratorName = decoratorNames.get(decorator);
							String className = decoratorName + "." + decorator;
							
							metaFactory = Class.forName(className, true, sysLoader);
							
							decoratorConstructors.add(metaFactory.getConstructor(interfaceClass));
			    		}
			    		
		        		Object interfaceObject = interfaceClass;
		        		
		        		System.out.println(decoratorConstructors);
		        		
						Object pizza = instantiate(decoratorConstructors, interfaceClass, decoratorConstructors.size() - 1, pizzaSimplesConstructor);
	
						interfaceMethodPrepare.invoke(pizza);
						
					}catch (Exception e1){
						e1.printStackTrace();
					}
	        	}

				private Object instantiate(ArrayList<Constructor> decoratorConstructors, Class interfaceClass, int i, Constructor pizzaSimplesConstructor) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
					Object pizza = null;
					
					if(i > -1) {
						System.out.println("\nInstanciando " + decoratorConstructors.get(i));
						pizza = decoratorConstructors.get(i).newInstance(instantiate(decoratorConstructors, interfaceClass, --i, pizzaSimplesConstructor));
						return pizza;
					}else {
						return pizzaSimplesConstructor.newInstance();
					}
				}
			};
			
			Object mainWindow = createWindow.invoke(null, Collections.list(decoratorNames.keys()), (Object)prepareListener);
	    }catch(Exception e1) {
	    	e1.printStackTrace();
	    }   
    }
}

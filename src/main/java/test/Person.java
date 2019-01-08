package test;

import sonntag.properties.ObjectP;
import sonntag.properties.Properties;

@Properties(
        properties = {
                Human.class,
                Man.class
        }
)
public class Person extends ObjectP implements Runnable {

        @Override
        public void run() {

        }
}

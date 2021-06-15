package frangoDecorator;

import interfaces.PizzaComponent;
import interfaces.PizzaDecorator;

public class FrangoDecorator extends PizzaDecorator {
    public FrangoDecorator(PizzaComponent decorated) {
       this.decorated = decorated;
    }
    public void preparar() {
        decorated.preparar();
        System.out.println("Colocando o frango");
    }    
}

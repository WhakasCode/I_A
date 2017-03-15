
package mouserun.mouse;

import java.util.ArrayList;
import mouserun.game.Mouse;
import mouserun.game.Grid;
import mouserun.game.Cheese;

public class M17D13 extends Mouse {

    public class Visita {

        private Grid casilla;
        private int nVeces;

        public Visita(Grid c) {
            this.casilla = c;
            this.nVeces = 0;
        }

        public Grid getCasilla() {
            return casilla;
        }

        public void setCasilla(Grid casilla) {
            this.casilla = casilla;
        }

        public int getnVeces() {
            return nVeces;
        }

        public void setnVeces(int nVeces) {
            this.nVeces = nVeces;
        }

        public void incremento() {
            this.nVeces++;
        }

        public void set1000() {
            this.nVeces = 1000;
        }

    }
    //Para saber la casilla en la que estoy antes de decidir movimiento
    private Grid casillaPadre = new Grid(0, 0);
    //Aquí guardo las casillas visitadas y el número de veces que la ha visitado
    private ArrayList<Visita> visitado = new ArrayList<>();

    public M17D13() {
        super("Prototype_1");

    }

    @Override
    public int move(Grid cActual, Cheese[] cheese) {

        
        //Miro a donde puedo ir y guardo las posibilidades
        ArrayList<Integer> posibilidades = new ArrayList<>();
        if (cActual.canGoUp()) {
            posibilidades.add(Mouse.UP);
        }
        
        if (cActual.canGoDown()) {
            posibilidades.add(Mouse.DOWN);
        }
        
        if (cActual.canGoLeft()) {
            posibilidades.add(Mouse.LEFT);
        }

        if (cActual.canGoRight()) {
            posibilidades.add(Mouse.RIGHT);
        }
        //posibilidades.add(Mouse.BOMB);

        int x, y, xx = 0, yy = 0;
        int drccn = 0;
        int nV = 0;
        int nVn = 0;
        int goTo = 0;
        boolean flag;
        
        //Recorro las posibilidades
        for (int i = 0; i < posibilidades.size(); ++i) {
            flag = false;
            x = cActual.getX();
            y = cActual.getY();
            //Veo la casilla que obtendría con cada movimiento y
            // me quedo con el movimiento necesario para ir a dicha casilla
            switch (posibilidades.get(i)) {
                case Mouse.UP:

                    y += 1;
                    drccn = 1;
                    break;

                case Mouse.DOWN:

                    y -= 1;
                    drccn = 2;
                    break;

                case Mouse.LEFT:

                    x -= 1;
                    drccn = 3;
                    break;

                case Mouse.RIGHT:

                    x += 1;
                    drccn = 4;
                    break;
            }
            
            //Marca las casillas con 3 paredes
            if (posibilidades.size() == 1) {
                for (int j = 0; j < visitado.size(); ++j) {
                    if (casillaPadre.getX() == visitado.get(j).getCasilla().getX() && casillaPadre.getY() == visitado.get(j).getCasilla().getY()) {
                        visitado.get(j).set1000();
                    }
                }
            }

            //
            Grid c = new Grid(x, y);
            //Si las ya he visitado alguna casilla miro para buscar
            // si ya la he visitado(pongo flag = true)
            if (!visitado.isEmpty()) {

                for (int j = 0; j < visitado.size(); ++j) {
                    if (x == visitado.get(j).getCasilla().getX() && y == visitado.get(j).getCasilla().getY()) {

                        flag = true;
                        nV = visitado.get(j).getnVeces();
                    }
                }
            }
            //Si no he visitado ninguna casilla o si la casilla que voy a 
            // visitar es nueva, creo una Visita y la guardo
            if (!flag) {

                Visita v = new Visita(c);
                v.incremento();
                incExploredGrids();
                visitado.add(v);
                casillaPadre = c;
                return drccn;

            } else { //Aquí me quedo con la casilla de las posibles a acceder,
                     // que menos veces haya visitado

                if (nVn == 0) {
                    xx = x;
                    yy = y;
                    goTo = drccn;
                    nVn = nV;

                } else {
                    if (nVn > nV) {
                        xx = x;
                        yy = y;
                        nVn = nV;
                        goTo = drccn;
                    }

                }

            }

        }
        //Recorro las Visitas para aumentar el número de veces que la visito
        for (int k = 0; k < visitado.size(); ++k) {
            if (xx == visitado.get(k).getCasilla().getX() && yy == visitado.get(k).getCasilla().getY()) {
                visitado.get(k).incremento();

            }
        }

        return goTo;

    }

    @Override
    public void newCheese() {

    }

    @Override
    public void respawned() {
    }

}

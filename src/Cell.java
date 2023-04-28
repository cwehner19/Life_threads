public class Cell implements Update {
    private boolean isAlive;

    public Cell(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }


    @Override
    public void Update() {

    }

    @Override
    public void Update(int numAliveNeighbors) {
        if (isAlive) {
            // An alive cell with 2 or 3 alive neighbors stays alive; otherwise, it dies.
            isAlive = numAliveNeighbors == 2 || numAliveNeighbors == 3;
        } else {
            // A dead cell with exactly 3 alive neighbors comes to life.
            isAlive = numAliveNeighbors == 3;
        }
    }
}

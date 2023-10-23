// PENSEZ A INDIQUER PAR DES COMMENTAIRES LES MODIFICATIONS APPORTEES A CE SQUELETTE AU FUR
// ET A MESURE DE L'EVOLUTION DU CODE DEMANDEE DANS LE TP.

/**
 * Les objets instances de la classe Stock representent un ensemble de pieces,
 * empilees les unes sur les autres. Du fait de la disposition en piles, il
 * n'est
 * pas possible que deux operateurs saisissent deux pieces au meme moment.
 *
 */
class Stock {
    /**
     * Nombre de pieces dans la pile
     */
    private int nbPieces;
    /**
     * Nombre de pieces maximal dans la pile
     */
    private int maxCapacity;
    /**
     * Le nom du stock
     */
    private String nom;

    /**
     * Creer un nouvel objet instance de stock
     * 
     * @param nom         Le nom du nouveau stock
     * @param nbPieces    Le nombre de pieces initial
     * @param maxCapacity Le nombre de pieces max
     */
    public Stock(String nom, int nbPieces, int maxCapacity) {
        this.nom = nom;
        this.nbPieces = nbPieces;
        this.maxCapacity = maxCapacity;
    }

    /**
     * Poser une piece sur le haut de la pile de pieces
     * 2.1 - we synchronized `stocker()` and `destocker()`
     * 2.3 - The Execution won't be fixed. There is no particular order.
     */
    synchronized public void stocker(String workshopName) {
        while (maxCapacity == nbPieces) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nbPieces++;
        // 3.2 - There is only 2 workshops, if 1 of them is blocked, it will be woken up
        // 3.4 - As we wait in stock and destock, we need to put `while` + `notifyAll()`
        notifyAll();

        System.out.println("The workshop " + workshopName + " restock the stock " +
                nom + ".");
        afficher();
    }

    /**
     * Saisir une piece sur le haut de la pile de pieces
     * 2.1 - we synchronized `stocker()` and `destocker()`
     * 3.1 - We now have the case where there is no piece to destock
     * ----- We have to wait until there some more.
     */
    synchronized public void destocker(String workshopName) {
        // The while is needed, cause a workshop could have restock one stock but not
        // the one waiting
        // This is not true, at the moment, cause the only one which could wait is the
        // workshop 2, and the only one chihc could notify while a workshop is sleeping
        // are the workshops 1 and 1bis
        while (nbPieces <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nbPieces--;
        notifyAll();

        System.out.println("The workshop " + workshopName + " destock the stock " +
                nom + ".");
        afficher();
    }

    /**
     * Affiche l'etat de l'objet stock
     */
    public void afficher() {
        System.out.println("Le stock " + nom + " contient " + nbPieces + " piece(s).");
    }

}

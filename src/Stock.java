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
     * Le nom du stock
     */
    private String nom;

    /**
     * Creer un nouvel objet instance de stock
     * 
     * @param nom      Le nom du nouveau stock
     * @param nbPieces Le nombre de pieces initial
     */
    public Stock(String nom, int nbPieces) {
        this.nbPieces = nbPieces;
        this.nom = nom;
    }

    /**
     * Poser une piece sur le haut de la pile de pieces
     * 2.1 - we synchronized `stocker()` and `destocker()`
     * 2.3 - The Execution won't be fixed. There is no particular order.
     */
    synchronized public void stocker(String workshopName) {
        nbPieces++;
        // We need to wake up all stock just in case
        notifyAll();

        // System.out.println("The workshop " + workshopName + " restock the stock " +
        // nom + ".");
        // afficher();
    }

    /**
     * Saisir une piece sur le haut de la pile de pieces
     * 2.1 - we synchronized `stocker()` and `destocker()`
     * 3.1 - We now have the case where there is no piece to destock
     * ----- We have to wait until there some more.
     */
    synchronized public void destocker(String workshopName) {
        while (nbPieces <= 0) {
            // We will wait until we're good to go.
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nbPieces--;

        // System.out.println("The workshop " + workshopName + " destock the stock " +
        // nom + ".");
        // afficher();
    }

    /**
     * Affiche l'etat de l'objet stock
     */
    public void afficher() {
        System.out.println("Le stock " + nom + " contient " + nbPieces + " piece(s).");
    }

}

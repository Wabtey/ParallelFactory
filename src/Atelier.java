// PENSEZ A INDIQUER PAR DES COMMENTAIRES LES MODIFICATIONS APPORTEES A CE SQUELETTE AU FUR
// ET A MESURE DE L'EVOLUTION DU CODE DEMANDEE DANS LE TP.

/**
 * Les objets instances de la classe Atelier representent des ateliers de
 * transformation.
 * Le fonctionnement est le suivant : l'appel a transformer retire un element du
 * stock A,
 * attend 100 ms, puis ajoute un element au stock B.
 * La methode travailler() effectue n transformations successives, n etant un
 * parametre
 * fourni a la creation de l'objet.
 * 
 * 2.1 - Atlier hérite de Thread
 */
class Atelier extends Thread {

    /**
     * Le stock de fourniture de depart
     */
    private Stock A;
    /**
     * Le stock de produits transformes
     */
    private Stock B;

    /**
     * Le nombre de transformations effectuees lors d'un appel a
     * la methode travailler().
     */
    private int nbTransfo;

    private String name;

    /**
     * Construit un objet instance d'Atelier
     * 
     * @param A         Le stock de pieces de depart
     * @param B         Le stock de pieces transformees
     * @param nbTransfo Le nombre de transformations par appel a travailler()
     */
    public Atelier(String workshopName, Stock A, Stock B, int nbTransfo) {
        this.name = workshopName;
        this.A = A;
        this.B = B;
        this.nbTransfo = nbTransfo;
    }

    /**
     * Effectue une transformation
     */
    public void transformer() {
        A.destocker(name);
        // try {
        // Thread.sleep(100);
        // } catch (InterruptedException e) {
        // }
        B.stocker(name);
    }

    /**
     * Effectue nbTransfo transformations
     * 2.1 - Atelier impl the function `run()`, which replaces `fonctionner()`
     */
    public void run() {
        for (; nbTransfo > 0; nbTransfo--)
            transformer();
    }

}

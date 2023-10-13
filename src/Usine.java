// PENSEZ A INDIQUER PAR DES COMMENTAIRES LES MODIFICATIONS APPORTEES A CE SQUELETTE AU FUR
// ET A MESURE DE L'EVOLUTION DU CODE DEMANDEE DANS LE TP.

import java.util.logging.Logger;

/**
 * Les objets instances de la classe Usine represente une usine avec deux
 * ateliers.
 * Une instance d'Usine possede un stock de pieces a transformer ainsi qu'un
 * stock
 * de pieces finies initialement vide. Chacun des deux ateliers transforme la
 * moitie
 * des unites du stock a transformer.
 * La methode fonctionner() fait travailler successivement les deux ateliers et
 * affiche
 * l'etat des stocks a la fin des travaux.
 */
class Usine {
    /**
     * Stock de pieces a transformer
     * 2.1 - Modified to 500 to observe parallelism more accurately
     */
    Stock stockDepart = new Stock("de depart", 10);
    /**
     * Stock de pieces a mi-transformée
     */
    Stock stockIntermediaire = new Stock("intermédiaire", 0);
    /**
     * Stock de pieces transformees
     */
    Stock stockFin = new Stock("d'arrivee", 0);
    /**
     * Ateliers de transformation
     * 2.1 - We share the work between all workstation (250 each)
     */
    Atelier atelier1 = new Atelier("1", stockDepart, stockIntermediaire, 10);
    Atelier atelier2 = new Atelier("2", stockIntermediaire, stockFin, 10);

    /**
     * Effectuer le travail de l'usine
     * Utilise successivement chaque atelier pour transformer une piece et affiche
     * l'evolution de l'etat des stocks.
     */
    public void fonctionner() {
        // 2.1 - We are starting the two workshops
        atelier1.start();
        atelier2.start();

        // 2.1 - We add the two `join()` functions to wait the two threads to end
        // 2.1 - If we don't wait, the stock will be displayed in process
        try {
            atelier1.join();
        } catch (InterruptedException e) {
            Logger.getGlobal().warning("Thread " + atelier1.getName() + " interrupted");
        }
        try {
            atelier2.join();
        } catch (InterruptedException e) {
            Logger.getGlobal().warning("Thread " + atelier2.getName() + " interrupted");
        }

        stockDepart.afficher();
        stockFin.afficher();
    }

    /**
     * Point d'entree pour l'ensemble du TP.
     * 
     * @param args Non utilise
     */
    public static void main(String[] args) {
        // 2.1 - Add a stopwatch to mesure change effectivness
        long start = System.currentTimeMillis();

        // 1.3 - L'usine est créée.
        // 2.1 - Without any change the factory proceeds in 1.014s
        // 2.1 - With simple parallelism the factory proceeds in 0.52s
        // 2.1 - We increase the initial stock to 500; new time: 25.069s D:
        // 2.2 - initial stock -> 100.000 but no sleep; new time: 0.022s
        // 2.2 - I didn't manage to identify a problem (prbly solved in 2.1)
        Usine montainlibaie = new Usine();
        montainlibaie.fonctionner();

        long finish = System.currentTimeMillis();
        float timeElapsed = finish - start;
        System.out.println("Tasks completed in " + timeElapsed / 1000 + "s");
    }
}

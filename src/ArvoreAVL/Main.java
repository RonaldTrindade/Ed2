package ArvoreAVL;

public class Main {
    public static void main(String[] args) {
        Arvore arvore = new Arvore();
        No raiz = null;

        //inserindo
        raiz = arvore.inserir(raiz, 10);
        raiz = arvore.inserir(raiz, 20);
        raiz = arvore.inserir(raiz, 30);
        raiz = arvore.inserir(raiz, 40);
        raiz = arvore.inserir(raiz, 50);
        raiz = arvore.inserir(raiz, 25);
        raiz = arvore.inserir(raiz, 35);

        System.out.println("Árvore AVL em ordem após inserções:");
        arvore.emOrdem(raiz);

        // Removendo um nó



    }
}
package ArvoreAVL;

public class Arvore implements IarvoreAVL {


    @Override
    public int altura(No n) {
        // verifica se é nulo, se sim retorna 0, se não entrar na condição retorna a altura
        if (n == null) {
            return 0;
        }
        return n.getAltura();
    }

    @Override
    public int balanceamento(No n) {
        //verifica se o no é nulo,se sim returna 0, se não entra na condição retorna a
        // altura da esquerda - altura da direita
        if (n == null) {
            return 0;
        }
        return altura(n.getEsquerda()) - altura(n.getDireita());
    }

    @Override
    public No rotacaoDireita(No y) {
        //setando valores
        No x = y.getEsquerda();
        No z = x.getDireita();

        //realizar rotação simples
        x.setDireita(y);
        y.setEsquerda(z);

        //atualizar as alturas
        y.setAltura(Math.max(altura(y.getEsquerda()), altura(y.getDireita())) + 1);
        x.setAltura(Math.max(altura(x.getEsquerda()), altura(x.getDireita())) + 1);

        //retorna a nova raiz
        return x;
    }

    @Override
    public No rotacaoEsquerda(No x) {
        //setando valores
        No y = x.getDireita();
        No z = y.getEsquerda();

        //realizar rotação simples
        y.setDireita(x);
        x.setEsquerda(z);

        //atualizar as alturas
        x.setAltura(Math.max(altura(x.getEsquerda()), altura(x.getDireita())) + 1);
        z.setAltura(Math.max(altura(y.getEsquerda()), altura(y.getDireita())) + 1);

        //retorna a nova raiz
        return y;
    }

    @Override
    public No inserir(No no, int chave) {
        // Passo 1: Inserir o nó normalmente
        if (no == null)
            return (new No(chave));

        if (chave < no.getChave())
            no.setEsquerda(inserir(no.getEsquerda(), chave));
        else if (chave > no.getChave())
            no.setDireita(inserir(no.getDireita(), chave));
        else
            return no; // Chaves duplicadas não são permitidas

        // Passo 2: Atualizar a altura do nó atual
        no.setAltura(1 + Math.max(altura(no.getEsquerda()), altura(no.getDireita())));

        // Passo 3: Obter o fator de balanceamento deste nó
        int balanceamento = balanceamento(no);

        // Se o nó estiver desbalanceado, há 4 casos

        // Caso 1: Rotação à direita
        if (balanceamento > 1 && chave < no.getEsquerda().getChave())
            return rotacaoDireita(no);

        // Caso 2: Rotação à esquerda
        if (balanceamento < -1 && chave > no.getDireita().getChave())
            return rotacaoEsquerda(no);
        // Caso 3: Rotação dupla esquerda-direita
        if (balanceamento > 1 && chave > no.getEsquerda().getChave()) {
            no.setEsquerda(rotacaoEsquerda(no.getEsquerda()));
            return rotacaoDireita(no);
        }

        // Caso 4: Rotação dupla direita-esquerda
        if (balanceamento < -1 && chave < no.getDireita().getChave()) {
            no.setDireita(rotacaoDireita(no.getDireita()));
            return rotacaoEsquerda(no);
        }

        // Retornar o ponteiro do nó (inalterado)
        return no;
    }

    // Função para encontrar o nó com valor mínimo
    No valorMinimoNo(No no) {
        No atual = no;

        // O valor mais à esquerda será o menor
        while (atual.getEsquerda() != null)
            atual = atual.getEsquerda();

        return atual;

    }

    @Override
    public No removerNo(No raiz, int chave) {
        // Passo 1: Realizar remoção padrão de BST
        if (raiz == null)
            return raiz;

        if (chave < raiz.getChave())
            raiz.setEsquerda(removerNo(raiz.getEsquerda(), chave));
        else if (chave > raiz.getChave())
            raiz.setDireita(removerNo(raiz.getDireita(), chave));
        else {
            // Nó com apenas um filho ou nenhum filho
            if ((raiz.getEsquerda() == null) || (raiz.getDireita() == null)) {
                No temp = (raiz.getEsquerda() != null) ? raiz.getEsquerda() : raiz.getDireita();

                // Caso 1: Sem filhos
                if (temp == null) {
                    raiz = null;
                } else {
                    // Caso 2: Um filho
                    raiz = temp;
                }
            } else {
                // Nó com dois filhos: Obter o sucessor in-order (menor na subárvore direita)
                No temp = valorMinimoNo(raiz.getDireita());

                // Copiar o valor do sucessor
                raiz.setChave(temp.getChave());

                // Remover o sucessor
                raiz.setDireita(removerNo(raiz.getDireita(), temp.getChave()));
            }
        }

        // Se a árvore tinha apenas um nó, raiz será null agora
        if (raiz == null)
            return raiz;

        // Passo 2: Atualizar a altura do nó atual (somente se o nó não for null)
        if (raiz != null) {
            raiz.setAltura(Math.max(altura(raiz.getEsquerda()), altura(raiz.getDireita())) + 1);
        }

        // Passo 3: Obter o fator de balanceamento deste nó
        int balanceamento = balanceamento(raiz);

        // Se o nó estiver desbalanceado, há 4 casos

        // Caso 1: Rotação à direita
        if (balanceamento > 1 && balanceamento(raiz.getEsquerda()) >= 0)
            return rotacaoDireita(raiz);

        // Caso 2: Rotação à esquerda
        if (balanceamento < -1 && balanceamento(raiz.getDireita()) <= 0)
            return rotacaoEsquerda(raiz);

        // Caso 3: Rotação dupla esquerda-direita
        if (balanceamento > 1 && balanceamento(raiz.getEsquerda()) < 0) {
            raiz.setEsquerda(rotacaoEsquerda(raiz.getEsquerda()));
            return rotacaoDireita(raiz);
        }

        // Caso 4: Rotação dupla direita-esquerda
        if (balanceamento < -1 && balanceamento(raiz.getDireita()) > 0) {
            raiz.setDireita(rotacaoDireita(raiz.getDireita()));
            return rotacaoEsquerda(raiz);
        }

        return raiz;
    }


    @Override
    public void emOrdem(No no) {
        if (no != null) {
            emOrdem(no.getEsquerda());
            System.out.print(no.getChave() + " ");
            emOrdem(no.getDireita());
        }

    }
}

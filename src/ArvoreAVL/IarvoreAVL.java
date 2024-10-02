package ArvoreAVL;

public interface IarvoreAVL {


    public int altura(No n);

    public int balanceamento(No n);

    public No rotacaoDireita(No y);

    public No rotacaoEsquerda(No x);

    public No inserir(No no,int chave );

    public No remover(No raiz,int chave );

    public void  emOrdem(No no);

}

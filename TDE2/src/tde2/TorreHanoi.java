package TDE2;

import java.util.Scanner;
import java.util.Random;

class No {

  int valor;
  No proximo;

  public No(int valor) {
    this.valor = valor;
    this.proximo = null;
  }
}

class Pilha {

  private No topo;
  private int capacidade;

  public Pilha(int capacidade) {
    this.topo = null;
    this.capacidade = capacidade;
  }

  public void push(int valor) {
    if (!cheia()) {
      No novoNo = new No(valor);
      novoNo.proximo = topo;
      topo = novoNo;
    }
  }

  public boolean vazia() {
    return topo == null;
  }

  public boolean ordenado(boolean crescente) {
    if (vazia() || topo.proximo == null) {
      return true;
    }

    No atual = topo;
    while (atual.proximo != null) {
      if ((crescente && atual.valor > atual.proximo.valor) || (!crescente && atual.valor < atual.proximo.valor)) {
        return false;
      }
      atual = atual.proximo;
    }

    return true;
  }

  public void imprime(int torre) {
    No atual = topo;
    System.out.print("Torre " + torre + ": ");
    while (atual != null) {
      System.out.print(atual.valor + " ");
      atual = atual.proximo;
    }
    System.out.println();
  }

  public int pop() {
    if (!vazia()) {
      int valor = topo.valor;
      topo = topo.proximo;
      return valor;
    }
    return -1;
  }

  public int peek() {
    if (!vazia()) {
      return topo.valor;
    }
    return -1;
  }

  public boolean cheia() {
    int tamanho = 0;
    No atual = topo;
    while (atual != null) {
      tamanho++;
      atual = atual.proximo;
    }
    return tamanho == capacidade;
  }

  public boolean completo(boolean crescente) {
    return cheia() && ordenado(crescente);
  }

  public int getTamanho() {
    return capacidade;
  }
}

public class TorreHanoi {

  private static void automatico(Pilha posicaoAtual, Pilha proxPosicao, boolean crescente) {
    Pilha pilhaAuxiliar = new Pilha(posicaoAtual.getTamanho());

    while (!posicaoAtual.vazia()) {
      int elemento = posicaoAtual.pop();
      pilhaAuxiliar.push(elemento);
    }

    while (!pilhaAuxiliar.vazia()) {
      int elemento = pilhaAuxiliar.pop();
      boolean movido = false;

      while (!proxPosicao.vazia()
          && ((crescente && proxPosicao.peek() > elemento)
              || (!crescente && proxPosicao.peek() < elemento))) {
        int temp = proxPosicao.pop();
        posicaoAtual.push(temp);
        movido = true;
      }

      if (!movido) {
        proxPosicao.push(elemento);
      } else {
        proxPosicao.push(elemento);
        while (!posicaoAtual.vazia()) {
          int temp = posicaoAtual.pop();
          proxPosicao.push(temp);
        }
      }
    }
  }

  private static Pilha getPilha(int numeroPilha, Pilha pilha1, Pilha pilha2, Pilha pilha3) {
    return switch (numeroPilha) {
      case 1 ->
        pilha1;
      case 2 ->
        pilha2;
      case 3 ->
        pilha3;
      default ->
        null;
    };
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();

    System.out.println("Exercicio Torre de Hanoi - TDE2");
    System.out.println("Qual sera o tamanho das pilhas?");
    int tamanho = scanner.nextInt();
    scanner.nextLine();

    Pilha pilha1 = new Pilha(tamanho);
    Pilha pilha2 = new Pilha(tamanho);
    Pilha pilha3 = new Pilha(tamanho);

    for (int i = 0; i < tamanho; i++) {
      pilha1.push(rand.nextInt(100) + 1);
    }

    pilha1.push(tamanho);

    pilha1.imprime(1);
    pilha2.imprime(2);
    pilha3.imprime(3);

    int cont = 0;
    boolean crescente = true;
    System.out.println("");
    System.out.println("Qual sera o modo de jogo?:");
    System.out.println("1 - Crescente");
    System.out.println("2 - Decrescente");
    System.out.println("");
    int escolha = scanner.nextInt();
    scanner.nextLine();
    if (escolha == 2) {
      crescente = false;
    }

    boolean jogo = true;
    while (jogo == true) {
      System.out.println("");
      System.out.println("Selecione uma opcao");
      System.out.println("1 - Movimentar");
      System.out.println("2 - Solucao automatica");
      System.out.println("0 - Sair do jogo");
      System.out.println("");
      int opcao = scanner.nextInt();
      scanner.nextLine();

      switch (opcao) {
        case 0 -> {
          jogo = false;
        }
        case 2 -> {
          Pilha pilhaOrigem = null, pilhaDestino = null;
          if (crescente) {
            if (!pilha1.vazia() && (pilha2.vazia() || pilha1.peek() <= pilha2.peek())) {
              pilhaOrigem = pilha1;
              pilhaDestino = pilha2;
            } else if (!pilha1.vazia() && (pilha3.vazia() || pilha1.peek() <= pilha3.peek())) {
              pilhaOrigem = pilha1;
              pilhaDestino = pilha3;
            } else if (!pilha2.vazia() && (pilha3.vazia() || pilha2.peek() <= pilha3.peek())) {
              pilhaOrigem = pilha2;
              pilhaDestino = pilha3;
            }
          } else {
            if (!pilha1.vazia() && (pilha2.vazia() || pilha1.peek() >= pilha2.peek())) {
              pilhaOrigem = pilha1;
              pilhaDestino = pilha2;
            } else if (!pilha1.vazia() && (pilha3.vazia() || pilha1.peek() >= pilha3.peek())) {
              pilhaOrigem = pilha1;
              pilhaDestino = pilha3;
            } else if (!pilha2.vazia() && (pilha3.vazia() || pilha2.peek() >= pilha3.peek())) {
              pilhaOrigem = pilha2;
              pilhaDestino = pilha3;
            }
          }
          if (pilhaOrigem != null && pilhaDestino != null) {
            automatico(pilhaOrigem, pilhaDestino, crescente);
            cont++;
            System.out.println("Ajuda automatica feita");
            pilha1.imprime(1);
            pilha2.imprime(2);
            pilha3.imprime(3);
            if ((crescente && pilha1.completo(true)
                || pilha2.completo(true)
                || pilha3.completo(true))
                || (!crescente && pilha1.completo(false)
                    || pilha2.completo(false)
                    || pilha3.completo(false))) {
              System.out.println("Ordenacao concluida em " + cont + " jogadas.");
              jogo = false;
            }
          } else {
            System.out.println("");
            System.out.println("solucao automatica impossivel");
            System.out.println("");
          }
        }
        case 1 -> {
          System.out.println("");
          System.out.println("O numero que voce quer mover esta em qual torre?");
          System.out.println("");

          int atual = scanner.nextInt();
          scanner.nextLine();

          System.out.println("");
          System.out.println("Para qual torre deseja mover o numero?");
          System.out.println("");

          int proximo = scanner.nextInt();
          scanner.nextLine();
          Pilha pilhaAtual = getPilha(atual, pilha1, pilha2, pilha3);
          Pilha proxPilha = getPilha(proximo, pilha1, pilha2, pilha3);
          if (pilhaAtual != null && proxPilha != null) {
            if (!pilhaAtual.vazia() && (proxPilha.vazia()
                || (crescente && pilhaAtual.peek() >= proxPilha.peek())
                || (!crescente && pilhaAtual.peek() <= proxPilha.peek()))) {
              int elemento = pilhaAtual.pop();
              proxPilha.push(elemento);
              cont++;
              pilha1.imprime(1);
              pilha2.imprime(2);
              pilha3.imprime(3);
              if ((crescente && pilha1.completo(true)
                  || pilha2.completo(true)
                  || pilha3.completo(true))
                  || (pilha1.completo(false) && !crescente
                      || pilha2.completo(false)
                      || pilha3.completo(false))) {
                System.out.println("Ordenacao concluida em " + cont + " jogadas.");
                jogo = false;
              }
            } else {
              System.out.println("");
              System.out.println("O numero nao pode ser colocado sobre um numero menor. Tente novamente!");
              System.out.println("");
              pilha1.imprime(1);
              pilha2.imprime(2);
              pilha3.imprime(3);
            }
          } else {
            System.out.println("");
            System.out.println("Tente novamente. A pilha atual ou a proxima esta invaldia");
            System.out.println("");
            pilha1.imprime(1);
            pilha2.imprime(2);
            pilha3.imprime(3);
          }
        }
        default ->
          System.out.println("Opção invalida. Tente novamente.");
      }
    }
  }
}

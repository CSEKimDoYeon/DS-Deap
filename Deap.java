public class Deap {
	int[] deap;
	int n = 0; // deap�� ����Ǵ� ������ ����, ��Ʈ�� ���������.

	public Deap(int maxSize) {
		deap = new int[maxSize];
	}

	// �ε��� i�� max-heap�� ��ġ�� ������ true�� �����ϰ�, �׷��� ������ false�� �����Ѵ�
	private boolean inMaxHeap(int i) {
		while (i > 2) { // ��Ʈ Ž��
			i = (i - 1) / 2;
		}
		// �ִ������϶� ��Ʈ i = 2, �ּ������϶� ��Ʈ i = 1
		if (i == 2) {
			return true;
		} else {
			return false;
		}
	}

	// �ε��� pos�� min-heap�� ��ġ�� ������ max partner�� �ε����� �����Ѵ�
	private int maxPartner(int pos) {
		Double ofs = Math.floor(Math.log(pos + 1) / Math.log(2)) - 1;
		int pos_minh = (int) (pos + Math.pow(2, ofs));
		if (pos_minh > n) { // pos_minh�� ������ �������� Ŭ ���
			pos_minh = (pos_minh - 1) / 2; // (j-1)/2
		}
		return pos_minh;
	}

	// �ε��� pos�� max-heap�� ��ġ�� ������ min partner�� �ε����� �����Ѵ�
	private int minPartner(int pos) {
		int index_minp;

		Double ofs = Math.floor(Math.log(pos + 1) / Math.log(2)) - 1;
		index_minp = (int) (pos - Math.pow(2, ofs));

		return index_minp;
	}

	// min-heap�� �ִ� �ε��� at ��ġ�� key�� ����
	private void minInsert(int at, int key) {
		for (int parent; (parent = (at - 1) / 2) != 0 && key < deap[parent]; deap[at] = deap[parent], at = parent)
			;
		deap[at] = key;
	}

	// max-heap�� �ִ� �ε��� at ��ġ�� key�� ����
	private void maxInsert(int at, int key) {
		for (int parent; (parent = (at - 1) / 2) != 0 && key > deap[parent]; deap[at] = deap[parent], at = parent)
			;
		deap[at] = key;
	}

	// max ���� �����Ͽ� �����Ѵ�
	public int deleteMax() {
		int a = 2;
		int tmp_deap = deap[n--];

		while (a * 2 + 1 < n) {
			if (deap[a * 2 + 1] > deap[a * 2 + 2]) {
				deap[a] = deap[a * 2 + 1];
				a = a * 2 + 1;
			} else {
				deap[a] = deap[a * 2 + 2];
				a = a * 2 + 2;
			}
		}
		int min = minPartner(a);
		if (min * 2 - 1 < n) {
			if (deap[min * 2 + 1] > deap[min * 2 + 2]) {
				min = min * 2 + 1;
			} else {
				min = min * 2 + 2;
			}
		}
		if (tmp_deap < deap[min]) {
			deap[a] = deap[min];
			minInsert(min, tmp_deap);
		} else {
			deap[a] = tmp_deap;
		}
		return tmp_deap;
	}

	// min ���� �����Ͽ� �����Ѵ�
	public int deleteMin() {
		int a = 1;
		int tmp_deap = deap[n--];

		while (a * 2 + 1 < n) {
			if (deap[a * 2 + 1] < deap[a * 2 + 2]) {
				deap[a] = deap[a * 2 + 1];
				a = a * 2 + 1;
			} else {
				deap[a] = deap[a * 2 + 2];
				a = a * 2 + 2;
			}
		}
		int max = maxPartner(a);
		if (tmp_deap > deap[max]) {
			deap[a] = deap[max];
			maxInsert(max, tmp_deap);
		} else {
			deap[a] = tmp_deap;
		}
		return tmp_deap;

	}

	// x�� �����Ѵ�, ������ �ʿ� ����.
	public void insert(int x) {
		if (n == deap.length - 1) {
			System.out.println("The heap is full");
			System.exit(1);
		}
		n++;
		if (n == 1) {
			deap[1] = x;
			return;
		}
		if (inMaxHeap(n)) {
			int i = minPartner(n);
			if (x < deap[i]) {
				deap[n] = deap[i];
				minInsert(i, x);
			} else {
				maxInsert(n, x);
			}
		} else {
			int i = maxPartner(n);
			if (x > deap[i]) {
				deap[n] = deap[i];
				maxInsert(i, x);
			} else {
				minInsert(n, x);
			}
		}
	}

	// Deap�� Ʈ�� �������� ����Ʈ�Ѵ�, ������ �ʿ� ����.
	public void print() {
		int levelNum = 2;
		int thisLevel = 0;
		int gap = 8;
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j < gap - 1; j++) {
				System.out.print(" ");
			}
			if (thisLevel != 0) {
				for (int j = 0; j < gap - 1; j++) {
					System.out.print(" ");
				}
			}
			if (Integer.toString(deap[i]).length() == 1) {
				System.out.print(" ");
			}
			System.out.print(deap[i]);
			thisLevel++;
			if (thisLevel == levelNum) {
				System.out.println();
				thisLevel = 0;
				levelNum *= 2;
				gap /= 2;
			}
		}
		System.out.println();
		if (thisLevel != 0) {
			System.out.println();
		}
	}

	// ���� �Լ� �ۼ�
	public static void main(String[] argv) {
		System.out.println("start");
		Deap deap = new Deap(1024);
		int[] data = { 235, 33, 88, 63, 242, 423, 253, 332, 444, 48, 29, 87, 999 };
		for (int i = 0; i < data.length; i++) {
			deap.insert(data[i]);
		}
		System.out.println("Initial Deap------------------------");
		deap.print();

		for (int a = 0; a < 3; a++) {
			System.out.println("Delete Min--------------------------");
			deap.deleteMin();
			deap.print();
		}

		for (int b = 0; b < 3; b++) {
			System.out.println("Delete Max--------------------------");
			deap.deleteMax();
			deap.print();
		}
	}
}

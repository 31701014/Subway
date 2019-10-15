package subway;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class road {

	static int MaxInt = 100000;
	static int i;
	static int stanum;// ����վ����
	static Map<String, Integer> map = new HashMap<String, Integer>();
	static Map<String, String> id = new HashMap<String,String>();
	public static boolean IsT(String name[], String nam, int n) {
		int i = 0;
		while (i < n) {
			if (name[i]!=null&&name[i].equals(nam)) {
				return true;
			}
			i++;
		}
		return false;
	} // ����ǲ��ǽڵ�

	static int Check(String name[], String nam, int n) {
		int i = 0;
		while (i < n && !name[i].equals(nam)) {
			i++;
		}
		return i;
	}

	static void print(int pa[], String nam[], int v, int i)// V��ʾ��Ŀ�ؽ��
	{
		if (pa[i] >= 0) {
			print(pa, nam, v, pa[i]);
			if(map.get(nam[pa[i]])==2)
				System.out.println(stanum + " " + nam[pa[i]]+"(�ɻ���"+id.get(nam[pa[i]])+")");
			else 
				System.out.println(stanum + " " + nam[pa[i]]);	
			stanum = stanum + 1;
		}
	}

	static double Dijkstra(double Map[], int path[], int n, int v, int d) {
		double min, ks;
		int u, t;
		double dist[] = new double[n + 1];
		int s[] = new int[n + 1];
		for (int i = 0; i < n; i++) {
			dist[i] = Map[n * v + i];
			s[i] = 0;
			if (i != v && dist[i] < MaxInt) {
				path[i] = v;
			} else
				path[i] = -1;
		}
		s[v] = 1;
		for (int i = 0; i < n - 1; i++) {
			min = MaxInt;
			u = v;
			for (int j = 0; j < n; j++) {
				if (s[j] == 0 && dist[j] < min) {
					u = j;
					min = dist[j];
				}
			}
			s[u] = 1;
			for (t = 0; t < n; t++) {
				if (s[t] == 0 && dist[u] + Map[n * u + t] < dist[t]) {
					dist[t] = (float) (dist[u] + Map[n * u + t]);
					path[t] = u;
				}
			}
		}

		ks = dist[d];// ����ֻ������Ҫ���Ǹ�Ŀ��ֵ
		return ks;
//		delete dist;
	} // Dijkstra�㷨����

	public static void main(String[] args) {
		int n = 330;
		double Map[] = new double[330 * 330];
		double Ma[] = Map;
		for (int i = 0; i < n * n; i++)
			Map[i] = MaxInt;
		String name[] = new String[330];
		String nam[] = name;
		
		int path[] = new int[330];
		int pa[] = path;
		List list =Arrays.asList(name);
		int x=0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:/subway.txt"))));
		String lineTxt = null;
		while ((lineTxt = br.readLine()) != null) {
			String na=null;
			String[] names = lineTxt.split(" ");
			String iid=names[0];
			for(int i=0;i<names.length-1;i++)
				names[i]=names[i+1];
			for (String namer : names) {						
				if (map.keySet().contains(namer)) {
					int temp=0;
					for(int i=0;i<names.length;i++) {
						if(namer.equals(names[i])) temp++;
						}
					if(temp==1) {
						map.put(namer, (map.get(namer) + 1));
						id.put(namer,id.get(namer)+","+iid);
					}
				} else {
					name[x] =namer;
					x++;
					map.put(namer, 1);
					id.put(namer, iid);
				}
				if(na!=null) {
					Map[n * list.indexOf(namer) + list.indexOf(na)] = 2;
					Map[n * list.indexOf(na) + list.indexOf(namer)] = 2;		
				}
				na=namer;
			}
		}
		br.close();
		}catch (Exception e) {
            System.err.println("read errors :" + e);
        }
		for (i = 0; i < n; i++)
			Map[n * i + i] = 0;
		String start, dest,road;
		double race = 21;
		System.out.print("�������������ѯ��·Ϊa����ѯ���·��Ϊb��:");
		Scanner cin = new Scanner(System.in);
		String temp=cin.nextLine();
		if(temp.equals("a")) {
			System.out.print("�������ѯ��·:");
			road = cin.nextLine();
			try {
				BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:/subway.txt"))));
			String line = null;
			int have=0;
			while ((line = bw.readLine()) != null) {
				String[] names = line.split(" ");
				String iid=names[0];			
				if(iid.equals(road)) {
					have=1;
				for (String namer : names) {
					if(id.get(namer)!=null) {
					System.out.println(namer+"("+id.get(namer)+")");
					}
				}
			}			
		}
			if(have==0) System.out.println("��·������");
			bw.close();
			}catch (Exception e) {
	            System.err.println("����");
	        }
		}
		else if(temp.equals("b")) {
		System.out.print("���������վ��:");
		start = cin.nextLine();
		while (!IsT(name, start, n)) {
			System.out.print("��������������������վ��:");
			start = cin.nextLine();
		}
		System.out.print("�������յ�վ��:");
		dest = cin.nextLine();
		while (!IsT(name, dest, n)) {
			System.out.print("������������������յ�վ��:");
			dest = cin.nextLine();
		}
		if (IsT(name, dest, n)) {
			float distan = (float) Dijkstra(Ma, pa, n, Check(nam, start, n), Check(nam, dest, n));// �ܲ���˽�㵽���������·��
			int s, d;
			s = Check(nam, start, n);
			d = Check(nam, dest, n);
			stanum = 0;
			print(pa, nam, s, d);
			System.out.println(stanum + " " + dest);
			int fen = (int) (distan * 1000 / race / 60);
			int miao = ((int) (distan * 1000 / race)) % 60;
			int r;
			if (distan > 6) {
				if (distan > 12) {
					if (distan > 32) {
						r = (int) (6 + distan / 20);
					} else
						r = (int) (4 + distan / 10);
				} else
					r = 4;
			} else
				r = 3;

			System.out.println("�����о���Ϊ:" + distan + "km");
			System.out.println(";����վ����Ϊ:" + stanum);
			System.out.println("����ʱ��" + fen + "��" + miao + "��");
			System.out.println("��Ʊ��Ϊ:" + r + "Ԫ");

		} else {
			System.out.println();
			System.out.println("please choise your stations!");
			}
		}
		return;
	}

}

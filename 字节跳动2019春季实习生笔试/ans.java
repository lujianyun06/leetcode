    public void q1() {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt();
        n %= 1024;
        if (n == 0) {
            System.out.println(0);
            return;
        }
        n = 1024-n;
        int count = 0;
        int a = 64;
        do {
            count += n / (a);
            n %= a;
            a /= 4;

        } while (n > 0);
        System.out.println(count);
    }

    //    012345
//    aaaaab
    public void q2() {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt();
        for (int i = 0; i < n; i++) {
            String s = cin.next();
            if (s.length() <= 2) {
                System.out.println(s);
                continue;
            }
            StringBuilder builder = new StringBuilder(s);

            if (s.length() >= 3) {
                int a1 = 0, a2 = 1, a3 = 2;
                for (; a3 < s.length(); a1++, a2++, a3++) {
                    if (s.charAt(a1) == s.charAt(a2) && s.charAt(a1) == s.charAt(a3)) {
                        builder.replace(a3, a3 + 1, "@");
                    }
                }
            }

            if (builder.toString().length() >= 4) {
                int a1 = 0, a2 = 1, a3 = 2, a4 = 3;
                for (; a4 < builder.toString().length(); a1++, a2++, a3++, a4++) {
                    if (builder.toString().charAt(a1) == builder.toString().charAt(a2) && builder.toString().charAt(a3) == builder.toString().charAt(a4)) {
                        builder.replace(a4, a4 + 1, "@");
                    }
                }
            }
            String ans = builder.toString();
            ans = ans.replace("@", "");
            System.out.println(ans);
        }
    }

    public void q3() {
        Scanner cin = new Scanner(System.in);
        int cnt = cin.nextInt();
        while (cnt-- > 0) {
            int n = cin.nextInt();
            int[] score = new int[n];
            for (int i = 0; i < n; i++) {
                score[i] = cin.nextInt();
            }
            boolean changes = false;
            int[] award = new int[n]; //从1开始算便于计算
            for (int i = 0; i < n; i++) {
                award[i] = 1;
            }
            do {
                changes = false;
                for (int i = 0; i < n; i++) {
                    int l, r;
                    if (i == 0) l = n - 1;
                    else l = i - 1;
                    r = (i + 1) % n;
                    if (score[i] > score[l] && award[i] <= award[l]) {
                        award[i]++;
                        changes = true;
                    }
                    if (score[i] > score[r] && award[i] <= award[r]) {
                        award[i]++;
                        changes = true;
                    }
                }
            } while (changes);

            int count = 0;
            for (int i = 0; i < n; i++)
                count += award[i];
            System.out.println(count);
        }
    }

    public void q4() {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt();
        int m = cin.nextInt();
        long[] len = new long[n];
        int totalLen = 0;
        for (int i = 0; i < n; i++) {
            len[i] = cin.nextLong()*100; //扩100倍可以省去小数计算
            totalLen += len[i];
        }
        Arrays.sort(len);
        long limLen = totalLen / m; //最长不会超过的尺寸
        int count = 0;
        long maxLen = limLen;
        boolean found = false;

        while (maxLen >= 1) {
            count = 0;
            for (int i = n - 1; i >= 0; i--) {
                long tmp = (len[i] / maxLen);
                if (tmp == 0) break;
                count += tmp;
                if (count >= m) {
                    found = true;
                    break;
                }
            }
            if(found)break;
            else maxLen -= 1;
        }
        System.out.printf("%.2f", (double)maxLen/100);

    }
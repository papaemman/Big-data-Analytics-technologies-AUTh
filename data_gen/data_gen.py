import numpy as np
from matplotlib import pyplot as plt
import pandas as pnd


gen = np.random.default_rng(seed=12493)

for points in [3000, 10000]:
    for dim in [2, 5, 10]:
        a = 0.0055 * np.ones((dim,dim))
        a = a * (1 - np.tri(*a.shape)) + a * np.tri(*a.shape, k=-1) + 0.01 * np.eye(dim)
        b = 0.5 * np.ones(dim)
        c = -0.0055 * np.ones((dim,dim))
        c = c * (1 - np.tri(*c.shape)) + c * np.tri(*c.shape, k=-1) + 0.01 * np.eye(dim)

        d1 = gen.multivariate_normal(b, a, points)

        d2 = gen.uniform(0, 1, (points,dim))

        d3 = gen.normal(b, 0.125, [points, dim])

        d4 = gen.multivariate_normal(b, c, points)
        if(dim == 2):
            fig, axes = plt.subplots(nrows=1, ncols=4, figsize=[15, 3.75])
            axes[0].plot(d1[:, 0], d1[:, 1], linestyle='', fillstyle='none', marker='.')
            axes[0].set(xlim=(0, 1), ylim=(0, 1), xticks=(np.arange(0, 1.1, step=0.1)),
                        yticks=(np.arange(0, 1.1, step=0.1)))
            axes[1].plot(d2[:, 0], d2[:, 1], linestyle='', fillstyle='none', marker='.')
            axes[1].set(xlim=(0, 1), ylim=(0, 1), xticks=(np.arange(0, 1.1, step=0.1)),
                        yticks=(np.arange(0, 1.1, step=0.1)))
            axes[2].plot(d3[:, 0], d3[:, 1], linestyle='', fillstyle='none', marker='.')
            axes[2].set(xlim=(0, 1), ylim=(0, 1), xticks=(np.arange(0, 1.1, step=0.1)),
                        yticks=(np.arange(0, 1.1, step=0.1)))
            axes[3].plot(d4[:, 0], d4[:, 1], linestyle='', fillstyle='none', marker='.')
            axes[3].set(xlim=(0, 1), ylim=(0, 1), xticks=(np.arange(0, 1.1, step=0.1)),
                        yticks=(np.arange(0, 1.1, step=0.1)))
            fig.tight_layout()
            plt.savefig("points-%d.png" % points)
        col = []
        for i in range(1, dim + 1):
            col.append("d%d" % i)

        df = pnd.DataFrame(data=d1, columns=col)
        df.to_csv("correlated_%d_%dD.csv" % (points,dim))

        df = pnd.DataFrame(data=d2, columns=col)
        df.to_csv("uniform_%d_%dD.csv" % (points,dim))

        df = pnd.DataFrame(data=d3, columns=col)
        df.to_csv("normal_%d_%dD.csv" % (points,dim))

        df = pnd.DataFrame(data=d4, columns=col)
        df.to_csv("anticor_%d_%dD.csv" % (points,dim))

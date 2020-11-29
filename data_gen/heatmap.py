import pandas as pd

df = pd.read_csv("normal_3000_5D.csv", index_col=0)
print(df.head())
print(df.corr())
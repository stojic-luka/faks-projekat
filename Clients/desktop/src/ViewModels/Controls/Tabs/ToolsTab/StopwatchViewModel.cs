using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;
using AugmentedCooking.src.Helpers;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

namespace AugmentedCooking.src.ViewModels.Controls.Tabs.ToolsTab {
    public partial class StopwatchViewModel : ObservableObject {
        private readonly System.Timers.Timer _timer;

        private int _timeSeconds;
        public int TimeSeconds {
            get { return _timeSeconds; }
            set {
                if (_timeSeconds != value) {
                    _timeSeconds = value;
                    OnPropertyChanged();

                    OnPropertyChanged(nameof(HourAngle));
                    OnPropertyChanged(nameof(MinuteAngle));
                    OnPropertyChanged(nameof(SecondAngle));
                    OnPropertyChanged(nameof(DisplayTime));
                }
            }
        }

        [ObservableProperty]
        private bool _isRunning;

        public double HourAngle => TimeSeconds / 3600 % 12 * 30;
        public double MinuteAngle => TimeSeconds / 60 % 60 * 6;
        public double SecondAngle => TimeSeconds % 60 * 6;
        public string DisplayTime {
            get {
                int hours = TimeSeconds / 3600;
                int minutes = TimeSeconds % 3600 / 60;
                int seconds = TimeSeconds % 60;

                if (hours > 0)
                    return $"{hours:D2}:{minutes:D2}:{seconds:D2}";
                else
                    return $"{minutes:D2}:{seconds:D2}";
            }
        }

        public ICommand StartStopStopwatch { get; }
        public ICommand ResetStopwatch { get; }

        public StopwatchViewModel() {
            _timer = new System.Timers.Timer(1000);
            _timer.Elapsed += (sender, e) => TimeSeconds += 1;

            StartStopStopwatch = new RelayCommand(
                () => {
                    if (!IsRunning) {
                        _timer.Start();
                        IsRunning = true;
                    }
                    else {
                        _timer.Stop();
                        IsRunning = false;
                    }
                }
            );
            ResetStopwatch = new RelayCommand(
                () => {
                    _timer.Stop();
                    IsRunning = false;
                    TimeSeconds = 0;
                }
            );
        }

        public void Start() {
            _timer.Start();
            IsRunning = true;
        }

        public void Stop() {
            _timer.Stop();
            IsRunning = false;
        }

        public void Reset() {
            _timer.Stop();
            TimeSeconds = 0;
        }
    }
}
